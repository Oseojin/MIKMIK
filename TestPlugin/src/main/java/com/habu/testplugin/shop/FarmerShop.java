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

public class FarmerShop implements InventoryHolder
{
    final Inventory inv;
    static String configName = "farmershop";
    private static FileConfiguration shopConfig = TestPlugin.getConfigManager().getConfig(configName);

    private int[][] invBasic =
            { {1,1,1,1,1,1,1,1,1}
            , {1,2,2,2,2,2,2,2,1}
            , {1,2,2,0,0,0,0,0,1}
            , {1,0,0,0,0,0,0,0,1}
            , {1,0,0,0,0,0,0,0,1}
            , {1,1,1,1,1,1,1,1,1} };

    private Queue<ItemStack> sellItem = new LinkedList<ItemStack>();

    private static HashMap<Material, String> itemNamePare = new HashMap<Material, String>();

    private void initItemSetting()
    {
        itemNamePare.put(ItemManager.gui_FarmerWheat.getType(), "wheat");
        itemNamePare.put(ItemManager.gui_FarmerCarrot.getType(), "carrot");
        itemNamePare.put(ItemManager.gui_FarmerPotato.getType(), "potato");
        itemNamePare.put(ItemManager.gui_FarmerBeetroot.getType(), "beetroot");
        itemNamePare.put(ItemManager.gui_FarmerMelon.getType(), "melon");
        itemNamePare.put(ItemManager.gui_FarmerPumpkin.getType(), "pumpkin");
        itemNamePare.put(ItemManager.gui_FarmerSweet_Berries.getType(), "sweet_berries");
        itemNamePare.put(ItemManager.gui_FarmerCoCoa_Beans.getType(), "cocoa_beans");
        itemNamePare.put(ItemManager.gui_FarmerNether_Wart.getType(), "nether_wart");


        sellItem.add(ItemManager.gui_FarmerWheat);
        sellItem.add(ItemManager.gui_FarmerCarrot);
        sellItem.add(ItemManager.gui_FarmerPotato);
        sellItem.add(ItemManager.gui_FarmerBeetroot);
        sellItem.add(ItemManager.gui_FarmerMelon);
        sellItem.add(ItemManager.gui_FarmerPumpkin);
        sellItem.add(ItemManager.gui_FarmerSweet_Berries);
        sellItem.add(ItemManager.gui_FarmerCoCoa_Beans);
        sellItem.add(ItemManager.gui_FarmerNether_Wart);
    }

    public FarmerShop()
    {
        inv = Bukkit.createInventory(null, 54, "FarmerShop");
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
