package com.habu.testplugin.shop.jobshop;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.config.ConfigManager;
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

public class MinerShop implements InventoryHolder
{
    final Inventory inv;
    static String configName = "minershop";
    private static FileConfiguration shopConfig = TestPlugin.getConfigManager().getConfig(configName);

    private int[][] invBasic =
            { {1,1,1,1,1,1,1,1,1}
            , {1,2,2,2,2,2,2,2,1}
            , {1,2,2,2,2,0,0,0,1}
            , {1,0,0,0,0,0,0,0,1}
            , {1,0,0,0,0,0,0,0,1}
            , {1,1,1,1,1,1,1,1,1} };

    private Queue<ItemStack> sellItem = new LinkedList<ItemStack>();

    private static HashMap<Material, String> itemNamePare = new HashMap<Material, String>();

    private void initItemSetting()
    {
        itemNamePare.put(ItemManager.gui_MinerCoal.getType(), "coal");
        itemNamePare.put(ItemManager.gui_MinerCopper.getType(), "copper");
        itemNamePare.put(ItemManager.gui_MinerIron.getType(), "iron");
        itemNamePare.put(ItemManager.gui_MinerGold.getType(), "gold");
        itemNamePare.put(ItemManager.gui_MinerLapis_Lazuli.getType(), "lapis_lazuli");
        itemNamePare.put(ItemManager.gui_MinerRedstone.getType(), "redstone");
        itemNamePare.put(ItemManager.gui_MinerDiamond.getType(), "diamond");
        itemNamePare.put(ItemManager.gui_MinerEmerald.getType(), "emerald");
        itemNamePare.put(ItemManager.gui_MinerQuartz.getType(), "quartz");
        itemNamePare.put(ItemManager.gui_MinerAmethyst_Shard.getType(), "amethyst_shard");
        itemNamePare.put(ItemManager.gui_MinerNetherite.getType(), "netherite");

        sellItem.add(ItemManager.gui_MinerCoal);
        sellItem.add(ItemManager.gui_MinerCopper);
        sellItem.add(ItemManager.gui_MinerIron);
        sellItem.add(ItemManager.gui_MinerGold);
        sellItem.add(ItemManager.gui_MinerLapis_Lazuli);
        sellItem.add(ItemManager.gui_MinerRedstone);
        sellItem.add(ItemManager.gui_MinerDiamond);
        sellItem.add(ItemManager.gui_MinerEmerald);
        sellItem.add(ItemManager.gui_MinerQuartz);
        sellItem.add(ItemManager.gui_MinerAmethyst_Shard);
        sellItem.add(ItemManager.gui_MinerNetherite);
    }

    public MinerShop()
    {
        inv = Bukkit.createInventory(null, 54, "MinerShop");
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
        List<String> lore = new ArrayList<>();
        for(int i = 0; i < itemMeta.getLore().size() - 1; i++)
        {
            lore.add(itemMeta.getLore().get(i));
        }
        lore.add(ChatColor.GOLD + "[판매가] " + price);
        itemMeta.setLore(lore);
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
