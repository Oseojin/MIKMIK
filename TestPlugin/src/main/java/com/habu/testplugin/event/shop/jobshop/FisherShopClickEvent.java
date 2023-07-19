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
                Material material = clickedItem.getType();
                int price = FisherShop.GetPrice(material);

                if(playerInv.contains(material))
                {
                    int stack = 0;
                    if(event.isLeftClick() && !event.isShiftClick())
                    {
                        stack = SellOneItem(player, playerInv, material, uuid, price);
                    }
                    else if(event.isLeftClick() && event.isShiftClick())
                    {
                        stack = SellAllItem(player, playerInv, material, uuid, price);
                    }
                    FisherShop.SellItem(material, stack);
                }
            }
        }
    }

    private Integer CheckSize(ItemStack fish)
    {
        String size = ChatColor.stripColor(fish.getLore().get(0).replace(" 크기", ""));
        int magnification = 0;

        switch (size)
        {
            case "거대한":
                magnification = 500;
                break;
            case "큰":
                magnification = 5;
                break;
            case "보통":
                magnification = 2;
                break;
            case "작은":
                magnification = 1;
                break;
        }

        return magnification;
    }

    private Integer SellAllItem(Player player, Inventory playerInv, Material material, UUID uuid, Integer price)
    {
        int stack = 0;
        int allPrice = 0;
        for(int i = 0; i < playerInv.getSize(); i ++)
        {
            ItemStack invItem = playerInv.getItem(i);
            if(invItem == null || !invItem.getItemMeta().hasLore())
                continue;
            Material invItemMaterial = invItem.getType();
            if(invItemMaterial.equals(material))
            {
                int mag = CheckSize(invItem);
                allPrice += mag * price * invItem.getAmount();
                stack += invItem.getAmount();
                playerInv.removeItem(invItem);
            }
        }
        PlayerManager.AddGold(player, allPrice);
        player.sendMessage(material + " " + stack + " 개를 " + allPrice + " 골드에 판매하였습니다.");
        return stack;
    }

    private Integer SellOneItem(Player player, Inventory playerInv, Material material, UUID uuid, Integer price)
    {
        for(int i = 0; i < playerInv.getSize(); i ++)
        {
            ItemStack invItem = playerInv.getItem(i);
            if(invItem == null || !invItem.getItemMeta().hasLore())
                continue;
            Material invItemMaterial = invItem.getType();
            if(invItemMaterial.equals(material))
            {
                int mag = CheckSize(invItem);
                int amount = invItem.getAmount() - 1;
                invItem.setAmount(amount);
                PlayerManager.AddGold(player, price * mag);
                String size = ChatColor.stripColor(invItem.getLore().get(0).replace("크기", ""));
                player.sendMessage(size + material + " 1 개를 " + price * mag + " 골드에 판매하였습니다.");
                break;
            }
        }
        return 1;
    }
}
