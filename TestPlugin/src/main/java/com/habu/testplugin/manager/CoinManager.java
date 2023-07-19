package com.habu.testplugin.manager;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.config.ConfigManager;
import com.habu.testplugin.shop.CoinShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class CoinManager
{
    static String configName = "coinshop";
    private static FileConfiguration shopConfig = TestPlugin.getConfigManager().getConfig(configName);
    private static CoinManager instance;
    private static int maxDelistingCount = 60; // 3600

    private CoinManager()
    {

    }

    public static CoinManager getInstance()
    {
        if(instance == null)
        {
            synchronized (CoinManager.class)
            {
                instance = new CoinManager();
            }
        }
        return instance;
    }

    public static Boolean isNull()
    {
        if(instance == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void CoinReRoll()
    {
        new BukkitRunnable()
        {
            int currTime;

            @Override
            public void run()
            {
                if(CoinShop.GetOpen())
                {
                    currTime = shopConfig.getInt("changetimer.now");
                    if(currTime <= 0)
                    {
                        shopConfig.set("changetimer.now", shopConfig.getInt("changetimer.basic"));
                        TestPlugin.getConfigManager().saveConfig(configName);
                        CoinShop.initItemSetting();
                        CoinShop.reloadAllItems();
                    }
                    else
                    {
                        shopConfig.set("changetimer.now", --currTime);
                        TestPlugin.getConfigManager().saveConfig(configName);
                    }
                }
                else
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(TestPlugin.getPlugin(), 0, 20L);
    }

    public static void DelistingCoinCount(String coinName)
    {
        new BukkitRunnable()
        {
            int currTime;
            @Override
            public void run()
            {
                if(CoinShop.GetOpen())
                {
                    currTime = shopConfig.getInt(coinName + ".delisting_count");
                    if(currTime <= 0)
                    {
                        shopConfig.set(coinName + ".delisting", false);
                        shopConfig.set(coinName + ".delisting_count", maxDelistingCount);
                        shopConfig.set(coinName + ".price", shopConfig.get(coinName + ".basic_price"));
                        TestPlugin.getConfigManager().saveConfig(configName);
                    }
                    else
                    {
                        shopConfig.set(coinName + ".delisting_count", --currTime);
                    }
                }
            }
        }.runTaskTimer(TestPlugin.getPlugin(), 0, 20L);
    }

    public static String PungsanDogCoinName = ChatColor.GREEN + "[풍산개코인]";
    public static String MoleCoinName = ChatColor.YELLOW + "[두더지코인]";
    public static String BeetCoinName = ChatColor.LIGHT_PURPLE + "[비트코인]";
    public static String KimchiCoinName = ChatColor.RED + "[김치코인]";
}
