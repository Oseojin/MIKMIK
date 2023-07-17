package com.habu.testplugin.event.shop;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.shop.GeneralShop;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GeneralShopClickEvent implements Listener
{

    private static HashMap<Material, ItemStack> itemMap = new HashMap<Material, ItemStack>()
    {{
        put(Material.BRICK, ItemManager.item_AreaProtect);
        put(Material.TRIPWIRE_HOOK, ItemManager.item_DoorLock);
        put(Material.NETHER_STAR, ItemManager.item_InventorySave);
        put(Material.ENCHANTED_BOOK, ItemManager.item_WhiteListAdder);
        put(Material.PAPER, ItemManager.item_JobInitializer);
        put(Material.BOOK, ItemManager.item_ReturnVillage);
    }};

    @EventHandler
    public void onClickShop(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        Inventory inv = event.getClickedInventory();
        if(inv == null)
            return;

        String title = ChatColor.stripColor(event.getView().getTitle());

        if(title.equalsIgnoreCase("GeneralShop"))
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
                int price = GeneralShop.GetPrice(material);
                if(playerInv.firstEmpty() == -1)
                {
                    player.sendMessage("인벤토리 공간을 최소 1칸 비워주세요.");
                }
                else
                {
                    PurchaseItem(player, material, uuid, price);
                }
            }
        }
    }

    private void PurchaseItem(Player player, Material material, UUID uuid, Integer price)
    {
        ItemStack itemStack = itemMap.get(material);
        if(PlayerManager.GetGold(player) < price)
        {
            player.sendMessage("돈이 부족합니다.");
            return;
        }
        player.getInventory().addItem(itemStack);
        PlayerManager.UseGold(player, price);
        player.sendMessage(itemStack.getItemMeta().getDisplayName() + ChatColor.WHITE + "를 " + price + " 골드에 구매하였습니다.");
    }
}
