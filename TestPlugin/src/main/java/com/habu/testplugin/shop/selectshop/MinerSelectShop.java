package com.habu.testplugin.shop.selectshop;

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
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MinerSelectShop implements InventoryHolder
{
    final Inventory inv;
    static String configName = "minershop"; // 여기
    private static FileConfiguration shopConfig = TestPlugin.getConfigManager().getConfig(configName);

    private int[][] invBasic =
            { {1,1,1,1,1,1,1,1,1}
                    , {1,0,0,0,2,0,0,0,1}
                    , {1,1,1,1,1,1,1,1,1} };

    private Queue<ItemStack> sellItem = new LinkedList<ItemStack>();

    private static HashMap<Material, String> itemNamePare = new HashMap<Material, String>();

    private void initItemSetting()
    {
        itemNamePare.put(ItemManager.gui_MinerSelecter.getType(), "miner_selecter"); // 여기

        sellItem.add(ItemManager.gui_MinerSelecter); // 여기
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
        List<String> lore = new ArrayList<>();
        for(int i = 0; i < itemMeta.getLore().size() - 1; i++)
        {
            lore.add(itemMeta.getLore().get(i));
        }
        lore.add(ChatColor.GOLD + "[판매가] " + price);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public MinerSelectShop()
    {
        inv = Bukkit.createInventory(null, 27, "MinerSelectShop"); // 여기
        initItemSetting();
        reloadAllItems();
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
    public @NotNull Inventory getInventory()
    {
        return null;
    }
}
