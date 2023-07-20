package com.habu.testplugin.Hidden;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.manager.TitleNameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class LightningCharger implements Listener
{
    Random random = new Random();
    private static Integer charge = 0;
    private static String weaponName = ChatColor.AQUA + "[뇌창]";

    public static ItemStack weapon = ItemManager.buildWeapon(Material.TRIDENT, 1, weaponName, 15, Enchantment.LOYALTY, 5, ChatColor.YELLOW + "1차: 삼지창을 맞은 엔티티에게 1 ~ 10회 낙뢰 (충전량 5 소모)");

    @EventHandler
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        Inventory playerInv = player.getInventory();
        if(PlayerManager.GetTitle(player).equals(TitleNameManager.LightningCharger))
        {
            for(int i = 0; i < playerInv.getSize(); i++)
            {
                ItemStack invItem = playerInv.getItem(i);
                if(invItem == null)
                    continue;
                if(invItem.isSimilar(weapon))
                {
                    player.sendMessage("!!!");
                    invItem.setAmount(0);
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        Inventory playerInv = player.getInventory();
        if(PlayerManager.GetTitle(player).equals(TitleNameManager.LightningCharger))
        {
            playerInv.addItem(weapon);
        }
    }

    @EventHandler
    public void Interact(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
    }

    @EventHandler
    public void TheQualificationOfLightning(EntityDamageByEntityEvent event)
    {
        if(!event.getDamager().getType().equals(EntityType.LIGHTNING))
        {
            return;
        }

        if(event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if(PlayerManager.GetHidden(player, LightningChargerManager.LightningChargerName) > 0)
            {
                if(!LightningChargerManager.HiddenConfig.getBoolean(LightningChargerManager.LightningChargerName + ".quest"))
                {
                    player.sendTitle(ChatColor.YELLOW + "히든: 뇌창 퀘스트 완료", ChatColor.WHITE + "전직 NPC에게 돌아가세요");
                    LightningChargerManager.HiddenConfig.set(LightningChargerManager.LightningChargerName + ".quest", true);
                    TestPlugin.getConfigManager().saveConfig("hidden");
                }
            }
        }
    }

    @EventHandler
    public void ThrowWeapon(EntityDamageByEntityEvent event)
    {
        if(event.getDamager().getType().equals(EntityType.TRIDENT))
        {
            Entity hitEntity = event.getEntity();
            Trident trident = (Trident) event.getDamager();
            Player player = (Player) trident.getShooter();

            if(PlayerManager.GetTitle(player).equals(TitleNameManager.LightningCharger))
            {
                Location targetLoc = hitEntity.getLocation();

                Thread SeriesLightning = new Thread(
                        new Runnable()
                        {
                            int count = random.nextInt(9) + 1;
                            @Override
                            public void run()
                            {
                                new BukkitRunnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        if(count != -1)
                                        {
                                            if(count <= 0)
                                            {
                                                this.cancel();
                                            }
                                            else
                                            {
                                                hitEntity.getWorld().spawnEntity(targetLoc, EntityType.LIGHTNING);
                                                count--;
                                            }
                                        }
                                    }
                                }.runTaskTimer(TestPlugin.getPlugin(), 5L, 2L);
                            }
                        }
                );
                if(charge >= 5)
                {
                    SeriesLightning.run();
                    charge -= 5;
                    player.sendMessage(ChatColor.YELLOW + "충전량 5 소모 | 현재 잔량: " + charge);
                }
                else
                {
                    Charge(player);
                }
            }
        }
        else if (event.getDamager() instanceof Player)
        {
            Player player = (Player) event.getDamager();
            if(PlayerManager.GetTitle(player).equals(TitleNameManager.LightningCharger))
            {
                if(player.getItemInHand().isSimilar(weapon))
                {
                    Charge(player);
                }
            }
        }
    }

    private void Charge(Player player)
    {
            if(PlayerManager.GetTitle(player).equals(TitleNameManager.LightningCharger))
            {
                int classRate = PlayerManager.GetHidden(player, LightningChargerManager.LightningChargerName);
                switch (classRate)
                {
                    case 1:
                        charge += 1;
                        break;
                    case 2:
                        charge += 2;
                        break;
                    case 3:
                        charge += 3;
                        break;
                    case 4:
                        charge += 5;
                        break;
                }
                if(charge > 100)
                {
                    charge = 100;
                }
                player.sendMessage(ChatColor.YELLOW + "충전량: " + charge);
            }
    }
}
