package com.habu.testplugin.event.shop.selectshop;

import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.shop.WoodCutterSelectShop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class WoodCutterSelectShopClickEvent implements Listener
{
    private static HashMap<Material, ItemStack> itemMap = new HashMap<Material, ItemStack>()
    {{
        put(Material.PAPER, ItemManager.item_WoodCutterSelecter); // 여기
    }};

    @EventHandler
    public void onClickShop(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        Inventory inv = event.getClickedInventory();
        if(inv == null || inv.equals(player.getInventory()))
            return;

        String title = ChatColor.stripColor(event.getView().getTitle());

        if(title.equalsIgnoreCase("WoodCutterSelectShop")) // 여기
        {
            event.setCancelled(true);
            Inventory playerInv = player.getInventory();
            ItemStack clickedItem = event.getCurrentItem();
            if(clickedItem == null)
                return;
            if (!clickedItem.equals(ItemManager.gui_GrayGlassPane))
            {
                Material material = clickedItem.getType();
                int price = WoodCutterSelectShop.GetPrice(material); // 여기
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
