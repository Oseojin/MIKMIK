package com.habu.testplugin.shop.jobshop;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
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
                    , {1,2,2,2,2,0,0,0,1}
                    , {1,0,0,0,0,0,0,0,1}
                    , {1,0,0,0,0,0,0,0,1}
                    , {1,0,0,0,0,0,0,0,1}
                    , {1,1,1,1,1,1,1,1,1} };

    private Queue<ItemStack> sellItem = new LinkedList<ItemStack>();

    private static HashMap<Material, String> itemNamePare = new HashMap<Material, String>();

    private void initItemSetting()
    {
        itemNamePare.put(ItemManager.gui_FisherCod.getType(), "cod");
        itemNamePare.put(ItemManager.gui_FisherSalmon.getType(), "salmon");
        itemNamePare.put(ItemManager.gui_FisherTropical_Fish.getType(), "tropical_fish");
        itemNamePare.put(ItemManager.gui_FisherPufferfish.getType(), "pufferfish");

        sellItem.add(ItemManager.gui_FisherCod);
        sellItem.add(ItemManager.gui_FisherSalmon);
        sellItem.add(ItemManager.gui_FisherTropical_Fish);
        sellItem.add(ItemManager.gui_FisherPufferfish);
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
                    reloadItem(indexItem, itemNamePare.get(indexItem.getType()));
                    inv.setItem(index, indexItem);
                }
            }
        }
    }

    private void reloadItem(ItemStack itemStack, String itemName)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String pricepath = itemName + ".price";
        int price = shopConfig.getInt(pricepath);
        itemMeta.setLore(Arrays.asList(ChatColor.GOLD + "[판매가]", ChatColor.GREEN + "[작은] " + price, ChatColor.AQUA + "[보통] " + price * 2, ChatColor.YELLOW + "[큰] " + price * 5, ChatColor.RED + "[거대한] " + price * 100));
        itemStack.setItemMeta(itemMeta);
    }

    public static void SellItem(Material itemMaterial, int amount)
    {
        if(itemNamePare.containsKey(itemMaterial))
        {
            String itemName = itemNamePare.get(itemMaterial);
            String volumepath = itemName + ".volume";
            int volume = shopConfig.getInt(volumepath) + amount;
            shopConfig.set(volumepath, volume);
            TestPlugin.getConfigManager().saveConfig(configName);
        }
    }

    public static Integer GetPrice(Material itemMaterial)
    {
        if(itemNamePare.containsKey(itemMaterial))
        {
            String itemName = itemNamePare.get(itemMaterial);
            String path = itemName + ".price";
            return shopConfig.getInt(path);
        }
        return -2;
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