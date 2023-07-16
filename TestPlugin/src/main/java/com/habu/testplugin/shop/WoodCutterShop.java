package com.habu.testplugin.shop;

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

public class WoodCutterShop implements InventoryHolder
{

    final Inventory inv;
    static String configName = "woodcuttershop";
    private static FileConfiguration shopConfig = TestPlugin.getConfigManager().getConfig(configName);

    private int[][] invBasic =
            { {1,1,1,1,1,1,1,1,1}
            , {1,2,2,2,2,2,2,2,1}
            , {1,2,2,2,2,2,2,0,1}
            , {1,0,0,0,0,0,0,0,1}
            , {1,0,0,0,0,0,0,0,1}
            , {1,1,1,1,1,1,1,1,1} };

    private Queue<ItemStack> sellItem = new LinkedList<ItemStack>();

    private static HashMap<Material, String> itemNamePare = new HashMap<Material, String>();

    private void initItemSetting()
    {
        itemNamePare.put(ItemManager.gui_WoodCutterOak.getType(), "oak");
        itemNamePare.put(ItemManager.gui_WoodCutterSpruce.getType(), "spruce");
        itemNamePare.put(ItemManager.gui_WoodCutterBirch.getType(), "birch");
        itemNamePare.put(ItemManager.gui_WoodCutterJungle.getType(), "jungle");
        itemNamePare.put(ItemManager.gui_WoodCutterAcacia.getType(), "acacia");
        itemNamePare.put(ItemManager.gui_WoodCutterDark_Oak.getType(), "dark_oak");
        itemNamePare.put(ItemManager.gui_WoodCutterMangrove.getType(), "mangrove");
        itemNamePare.put(ItemManager.gui_WoodCutterCrimson_Stem.getType(), "crimson_stem");
        itemNamePare.put(ItemManager.gui_WoodCutterMushroom_Stem.getType(), "mushroom_stem");
        itemNamePare.put(ItemManager.gui_WoodCutterWarped_Stem.getType(), "warped_stem");
        itemNamePare.put(ItemManager.gui_WoodCutterApple.getType(), "apple");
        itemNamePare.put(ItemManager.gui_WoodCutterGolden_Apple.getType(), "golden_apple");
        itemNamePare.put(ItemManager.gui_WoodCutterEnchanted_Golden_Apple.getType(), "enchanted_golden_apple");


        sellItem.add(ItemManager.gui_WoodCutterOak);
        sellItem.add(ItemManager.gui_WoodCutterSpruce);
        sellItem.add(ItemManager.gui_WoodCutterBirch);
        sellItem.add(ItemManager.gui_WoodCutterJungle);
        sellItem.add(ItemManager.gui_WoodCutterAcacia);
        sellItem.add(ItemManager.gui_WoodCutterDark_Oak);
        sellItem.add(ItemManager.gui_WoodCutterMangrove);
        sellItem.add(ItemManager.gui_WoodCutterCrimson_Stem);
        sellItem.add(ItemManager.gui_WoodCutterMushroom_Stem);
        sellItem.add(ItemManager.gui_WoodCutterWarped_Stem);
        sellItem.add(ItemManager.gui_WoodCutterApple);
        sellItem.add(ItemManager.gui_WoodCutterGolden_Apple);
        sellItem.add(ItemManager.gui_WoodCutterEnchanted_Golden_Apple);
    }

    public WoodCutterShop()
    {
        inv = Bukkit.createInventory(null, 54, "WoodCutterShop");
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
        itemMeta.setLore(Arrays.asList(ChatColor.GOLD + "[판매가] " + price));
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
