package com.habu.testplugin.manager;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.shop.CoinShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class CoinManager
{
    private static CoinManager instance;
    private static Timer timer;

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

    public static void Stop()
    {
        timer.cancel();
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

    TimerTask coinReRollTask = new TimerTask()
    {
        @Override
        public void run()
        {
            CoinShop.initItemSetting();
            CoinShop.reloadAllItems();
        }
    };

    public static void CoinReRoll()
    {
        timer = new Timer();
        timer.schedule(instance.coinReRollTask, 0, 600000);
    }

    public static String PungsanDogCoinName = ChatColor.GREEN + "[풍산개코인]";
    public static String MoleCoinName = ChatColor.YELLOW + "[두더지코인]";
    public static String BeetCoinName = ChatColor.LIGHT_PURPLE + "[비트코인]";
    public static String KimchiCoinName = ChatColor.RED + "[김치코인]";
}
