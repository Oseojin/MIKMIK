package com.habu.testplugin.shop.jobshop;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.event.job.Fisher;
import com.habu.testplugin.manager.ItemManager;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class FisherShop implements InventoryHolder
{
    final Inventory inv;
    static String configName = "fishershop";
    private static FileConfiguration shopConfig = TestPlugin.getConfigManager().getConfig(configName);

    private int[][] invBasic =
            { {1,1,1,1,1,1,1,1,1}
                    , {1,2,2,2,2,2,2,0,1}
                    , {1,0,0,0,0,0,0,0,1}
                    , {1,0,0,0,0,0,0,0,1}
                    , {1,0,0,0,0,0,0,0,1}
                    , {1,1,1,1,1,1,1,1,1} };

    private Queue<ItemStack> sellItem = new LinkedList<ItemStack>();

    private static HashMap<ItemStack, String> itemNamePare = new HashMap<ItemStack, String>();

    private static final ItemStack gui_FisherAnchovy =ItemManager.buildCustomItem(CustomStack.getInstance("customitems:anchovy").getItemStack(), 1);
    private static final ItemStack gui_FisherSalmon = ItemManager.buildCustomItem(CustomStack.getInstance("customitems:salmon").getItemStack(), 1);
    private static final ItemStack gui_FisherCutlassFish = ItemManager.buildCustomItem(CustomStack.getInstance("customitems:cutlassfish").getItemStack(), 1);
    private static final ItemStack gui_FisherTuna = ItemManager.buildCustomItem(CustomStack.getInstance("customitems:tuna").getItemStack(), 1);
    private static final ItemStack gui_FisherShark = ItemManager.buildCustomItem(CustomStack.getInstance("customitems:shark").getItemStack(), 1);
    private static final ItemStack gui_FisherWhale = ItemManager.buildCustomItem(CustomStack.getInstance("customitems:whale").getItemStack(), 1);

    private void initItemSetting()
    {
        itemNamePare.put(gui_FisherAnchovy, "anchovy");
        itemNamePare.put(gui_FisherSalmon, "salmon");
        itemNamePare.put(gui_FisherCutlassFish, "cutlassfish");
        itemNamePare.put(gui_FisherTuna, "tuna");
        itemNamePare.put(gui_FisherShark, "shark");
        itemNamePare.put(gui_FisherWhale, "whale");

        sellItem.add(gui_FisherAnchovy);
        sellItem.add(gui_FisherSalmon);
        sellItem.add(gui_FisherCutlassFish);
        sellItem.add(gui_FisherTuna);
        sellItem.add(gui_FisherShark);
        sellItem.add(gui_FisherWhale);
    }

    public FisherShop()
    {
        inv = Bukkit.createInventory(null, 54, "FisherShop");
        initItemSetting();
        reloadAllItems();
    }

    private void reloadAllItems()
    {
        for(int i = 0; i < 6; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(invBasic[i][j] == 1)
                {
                    int index = i * 9 + j;
                    inv.setItem(index, ItemManager.gui_GrayGlassPane);
                }
                else if(invBasic[i][j] == 2)
                {
                    int index = i * 9 + j;
                    ItemStack indexItem = sellItem.poll();
                    reloadItem(indexItem);
                    inv.setItem(index, indexItem);
                }
            }
        }
    }

    private void reloadItem(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String pricepath = itemNamePare.get(itemStack) + ".price";
        int price = shopConfig.getInt(pricepath);
        itemMeta.setLore(Arrays.asList(ChatColor.GOLD + "[판매가] " + price + " (최대 크기 기준)"));
        itemStack.setItemMeta(itemMeta);
    }

    public static Integer GetPrice(ItemStack fish)
    {
        final int[] price = {-2};
        itemNamePare.forEach((key, value) -> {
            if(key.isSimilar(fish))
            {
                String path = value + ".price";

                price[0] =  shopConfig.getInt(path);
            }
        });
        return price[0];
    }

    public void open(Player player)
    {
        player.openInventory(inv);
    }

    @Override
    public Inventory getInventory()
    {
        return inv;
    }
}