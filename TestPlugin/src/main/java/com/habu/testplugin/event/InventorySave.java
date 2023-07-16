package com.habu.testplugin.event;

import com.habu.testplugin.command.IssuingCheckCommand;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventorySave implements Listener
{
    public Map<UUID, ItemStack[]> inventories = new HashMap();
    public Map<UUID, ItemStack[]> armor = new HashMap();

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (this.inventories.containsKey(uuid))
        {
            this.inventories.remove(uuid);
        }
        if (this.armor.containsKey(uuid))
        {
            this.armor.remove(uuid);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Inventory playerInv = player.getInventory();
        player.sendMessage("사망");

        if(playerInv.containsAtLeast(ItemManager.item_InventorySave, 1))
        {
            boolean findSaveItem = false;
            for(int i = 0; i < playerInv.getSize(); i++)
            {
                ItemStack invItem = playerInv.getItem(i);
                if(invItem == null)
                    continue;
                if(invItem.isSimilar(ItemManager.item_InventorySave) && !findSaveItem)
                {
                    player.sendMessage("아이템 소거");

                    int amount = invItem.getAmount() - 1;
                    invItem.setAmount(amount);
                }
                else if(invItem.getType().equals(Material.PAPER) && ChatColor.stripColor(invItem.getItemMeta().getDisplayName()).equals("수표"))
                {
                    player.sendMessage(player.getLocation() + "");
                    player.getWorld().dropItem(player.getLocation(), invItem);
                    playerInv.removeItem(invItem);
                }
            }

            this.inventories.put(uuid, player.getInventory().getContents());
            this.armor.put(uuid, player.getInventory().getArmorContents());
            event.getDrops().clear();
        }
        int gold = PlayerManager.GetGold(player);

        Entity killer = event.getPlayer().getKiller();

        if(killer instanceof Player && PlayerManager.GetJob((Player)killer).equals(ChatColor.BLACK + "[약탈자]"))
        {
            if(gold > 0)
            {
                player.getWorld().dropItem(player.getLocation(), IssuingCheckCommand.IssuingCheck(gold));
                PlayerManager.SetGold(player, 0);
            }
        }
        else
        {
            if(gold > 0)
            {
                int halfGold = Math.round(PlayerManager.GetGold(player) / 2);
                PlayerManager.SetGold(player, halfGold);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (this.inventories.containsKey(uuid))
        {
            player.getInventory().setContents((ItemStack[]) this.inventories.get(uuid));
            this.inventories.remove(uuid);
        }
        if (this.armor.containsKey(uuid))
        {
            player.getInventory().setArmorContents((ItemStack[]) this.armor.get(uuid));
            this.armor.remove(uuid);
        }
    }
}