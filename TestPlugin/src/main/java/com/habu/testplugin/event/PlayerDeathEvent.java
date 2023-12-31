package com.habu.testplugin.event;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import com.habu.testplugin.manager.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDeathEvent implements Listener
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
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Inventory playerInv = player.getInventory();

        if(playerInv.containsAtLeast(ItemManager.item_InventorySave, 1) || playerInv.containsAtLeast(ItemManager.item_InventorySave_Deleted, 1) || player.getInventory().getItemInOffHand().isSimilar(ItemManager.item_InventorySave) || player.getInventory().getItemInOffHand().isSimilar(ItemManager.item_InventorySave_Deleted))
        {
            boolean findSaveItem = false;
            for(int i = 0; i < playerInv.getSize(); i++)
            {
                ItemStack invItem = playerInv.getItem(i);
                if(invItem == null)
                    continue;
                if(invItem.isSimilar(ItemManager.item_InventorySave) && !findSaveItem)
                {
                    player.sendMessage(ChatColor.AQUA + "인벤토리 보호 쿠폰이 사라집니다.");

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

        int gold = TestPlugin.db_conn.GetGold(player);

        Entity killer = event.getPlayer().getKiller();
        /*player.getWorld().dropItem(player.getLocation(), IssuingCheckCommand.IssuingCheck(gold));
        PlayerManager.SetGold(player, 0);
        player.sendMessage(ChatColor.DARK_RED + "약탈자에게 모든 돈을 빼앗겼습니다!");*/

        if(gold > 0)
        {
            int halfGold = Math.round(TestPlugin.db_conn.GetGold(player) / 2);
            player.sendMessage(ChatColor.RED + "" + (TestPlugin.db_conn.GetGold(player) - halfGold) + "골드가 사라졌습니다...");
            TestPlugin.db_conn.SetGold(player, halfGold);
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