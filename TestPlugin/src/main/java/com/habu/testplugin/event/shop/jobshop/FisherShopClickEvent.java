package com.habu.testplugin.event.shop.jobshop;

import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.shop.jobshop.FisherShop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class FisherShopClickEvent implements Listener
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

        if(title.equalsIgnoreCase("FisherShop"))
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
                ItemStack fish = clickedItem.clone();
                fish.setLore(null);
                int price = FisherShop.GetPrice(clickedItem);

                if(event.isLeftClick() && !event.isShiftClick())
                {
                    SellOneItem(player, playerInv, fish, price);
                }
                else if(event.isLeftClick() && event.isShiftClick())
                {
                    SellAllItem(player, playerInv, fish, price);
                }
            }
        }
    }

    private void SellAllItem(Player player, Inventory playerInv, ItemStack fish, Integer price)
    {
        int stack = 0;
        int allPrice = 0;
        for(int i = 0; i < playerInv.getSize(); i ++)
        {
            ItemStack invItem = playerInv.getItem(i);
            if(invItem == null)
                continue;
            if(invItem.isSimilar(fish))
            {
                allPrice += price * invItem.getAmount();
                stack += invItem.getAmount();
                playerInv.removeItem(invItem);
            }
        }

        if(stack == 0)
            return;

        PlayerManager.AddGold(player, allPrice);
        player.sendMessage(fish.getItemMeta().getDisplayName() + " " + stack + " 마리를 " + allPrice + " 골드에 판매하였습니다.");
    }

    private void SellOneItem(Player player, Inventory playerInv, ItemStack fish, Integer price)
    {
        for(int i = 0; i < playerInv.getSize(); i ++)
        {
            ItemStack invItem = playerInv.getItem(i);
            if(invItem == null)
                continue;
            if(invItem.isSimilar(fish))
            {
                int amount = invItem.getAmount() - 1;
                invItem.setAmount(amount);
                PlayerManager.AddGold(player, price);
                player.sendMessage(fish.getItemMeta().getDisplayName() + " 1 마리를 " + price + " 골드에 판매하였습니다.");
                break;
            }
        }
    }
}