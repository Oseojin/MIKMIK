package com.habu.testplugin.shop.randomshop;

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

import java.util.*;

public class RandomSpawnEgg implements InventoryHolder
{
    final Inventory inv;
    static String configName = "randomshop";
    private static FileConfiguration shopConfig = TestPlugin.getConfigManager().getConfig(configName);

    private int[][] invBasic =
            { {1,1,1,1,1,1,1,1,1}
            , {1,0,0,0,2,0,0,0,1}
            , {1,1,1,1,1,1,1,1,1} };

    private Queue<ItemStack> sellItem = new LinkedList<ItemStack>();

    private static HashMap<Material, String> itemNamePare = new HashMap<Material, String>();

    private void initItemSetting()
    {
        itemNamePare.put(ItemManager.gui_SpawnEgg.getType(), "random_spawn");

        sellItem.add(ItemManager.gui_SpawnEgg);
    }

    public RandomSpawnEgg()
    {
        inv = Bukkit.createInventory(null, 27, "RandomSpawnEggShop");
        initItemSetting();
        reloadAllItems();
    }

    private void reloadAllItems()
    {
        for(int i = 0; i < 3; i++)
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
        String lore0 = itemStack.getLore().get(0);
        itemMeta.setLore(Arrays.asList(lore0, ChatColor.GOLD + "[판매가] " + price));
        itemStack.setItemMeta(itemMeta);
    }

    public static Integer GetPrice(Material itemMaterial)
    {
        if(itemNamePare.containsKey(itemMaterial))
        {
            String itemName = itemNamePare.get(itemMaterial);
            String path = itemName + ".price";
            int price = shopConfig.getInt(path);
            return price;
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
