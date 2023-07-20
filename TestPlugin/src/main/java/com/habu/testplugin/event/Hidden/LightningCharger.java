package com.habu.testplugin.event.Hidden;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class LightningCharger implements Listener
{
    Random random = new Random();
    private static Integer charge = 0;
    private static String weaponName = ChatColor.AQUA + "[뇌창]";
    private static Integer weaponRate = 1;

    public static ItemStack weapon = ItemManager.buildWeapon(Material.TRIDENT, 1, weaponName + "_" + weaponRate + "차", 15, Enchantment.LOYALTY, 5, ChatColor.YELLOW + "1차: 삼지창을 맞은 엔티티에게 1 ~ 10회 낙뢰 (충전량 5 소모)");
    @EventHandler
    public void Interact(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
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
                if(player.getItemInHand().isSimilar(weapon))
                {
                    Location targetLoc = hitEntity.getLocation();

                    Thread SeriesLightning = new Thread(
                            new Runnable()
                            {
                                int count = random.nextInt(9) + 1;
                                @Override
                                public void run()
                                {
                                    Bukkit.broadcastMessage(""+count);
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
                                    }.runTaskTimer(TestPlugin.getPlugin(), 10L, 10L);
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
                        Charge(player, player.getItemInHand());
                    }
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
                    Charge(player, player.getItemInHand());
                }
            }
        }
    }

    private void Charge(Player player, ItemStack item)
    {
            if(PlayerManager.GetTitle(player).equals(TitleNameManager.LightningCharger))
            {
                String strclassRate = item.getItemMeta().getDisplayName().replace(weaponName + "_", "").replace("차", "");
                int classRate = Integer.parseInt(strclassRate);
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
