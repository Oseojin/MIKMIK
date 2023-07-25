package com.habu.testplugin.event.shop.jobshop;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.shop.jobshop.FarmerShop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class FarmerShopClickEvent implements Listener
{
    @EventHandler
    public void onClickShop(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        Inventory inv = event.getClickedInventory();
        if(inv == null)
            return;

        String title = ChatColor.stripColor(event.getView().getTitle());

        if(title.equalsIgnoreCase("FarmerShop"))
        {
            if(inv.equals(player.getInventory()))
            {
                if(event.isLeftClick())
                {
                    event.setCancelled(true);
                    return;
                }
            }
            event.setCancelled(true);
            Inventory playerInv = player.getInventory();
            ItemStack clickedItem = event.getCurrentItem();
            if(clickedItem == null)
                return;
            if (!clickedItem.equals(ItemManager.gui_GrayGlassPane))
            {
                Material material = clickedItem.getType();
                int price = FarmerShop.GetPrice(material);

                if(playerInv.contains(material))
                {
                    if(event.isLeftClick() && !event.isShiftClick())
                    {
                        SellOneItem(player, playerInv, material, price);
                    }
                    else if(event.isLeftClick() && event.isShiftClick())
                    {
                        SellAllItem(player, playerInv, material, price);
                    }
                }
            }
        }
    }

    private void SellAllItem(Player player, Inventory playerInv, Material material, Integer price)
    {
        String prefix = "개를";
        int stack = 0;
        for(int i = 0; i < playerInv.getSize(); i ++)
        {
            ItemStack invItem = playerInv.getItem(i);
            if(invItem == null)
                continue;
            Material invItemMaterial = invItem.getType();

            if(invItemMaterial.equals(material))
            {
                if(material.equals(Material.NETHER_WART))
                {
                    prefix = "묶음을 ";
                    stack += invItem.getAmount() / 10;
                    if(stack > 0)
                    {
                        invItem.setAmount(invItem.getAmount() - (stack * 10));
                    }
                }
                else
                {
                    stack += invItem.getAmount();
                    playerInv.removeItem(invItem);
                }
            }
        }
        if(stack == 0)
            return;
        TestPlugin.db_conn.AddGold(player, price * stack);
        player.sendMessage(material + " " + stack + prefix + (price * stack) + " 골드에 판매하였습니다.");
    }

    private void SellOneItem(Player player, Inventory playerInv, Material material, Integer price)
    {
        String prefix = "개를";
        for(int i = 0; i < playerInv.getSize(); i ++)
        {
            ItemStack invItem = playerInv.getItem(i);
            if(invItem == null)
                continue;
            Material invItemMaterial = invItem.getType();
            if(invItemMaterial.equals(material))
            {
                int amount = invItem.getAmount() - 1;
                if(material.equals(Material.NETHER_WART))
                {
                    prefix = "묶음을 ";
                    amount = invItem.getAmount() - 10;
                    if(amount >= 0)
                    {
                        invItem.setAmount(amount);
                    }
                    else
                    {
                        continue;
                    }
                }
                else
                {
                    invItem.setAmount(amount);
                }
                TestPlugin.db_conn.AddGold(player, price);
                player.sendMessage(material + " 1 "+ prefix + price + " 골드에 판매하였습니다.");
                break;
            }
        }
    }
}
