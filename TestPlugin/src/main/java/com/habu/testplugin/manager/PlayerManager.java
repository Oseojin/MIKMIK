package com.habu.testplugin.manager;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerManager
{
    private static FileConfiguration PlayerConfig = TestPlugin.getConfigManager().getConfig("player");

    public static void SetName(Player player, String playerName)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".name";
        PlayerConfig.set(path, playerName);
        SavePlayerConfig();
        PlayerScoreboardManager.reloadScboard(player);
    }

    public static String GetName(Player player)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".name";
        return PlayerConfig.getString(path);
    }

    public static void SetTitle(Player player, String titleName)
    {
        UUID uuid = player.getUniqueId();
        String title = "";
        String prefix = "";
        String path_title = "players."+uuid+".title";
        String path_prefix = "players."+uuid+".prefix";

        switch (titleName)
        {
            case "운영자":
                title = TitleNameManager.Operator;
                prefix = TitleNameManager.Operator;
                break;
            case "무직":
                title = TitleNameManager.JobLess;
                prefix = TitleNameManager.JobLess;
                break;
            case "낚시꾼":
                title = TitleNameManager.Fisher1;
                prefix = TitleNameManager.Fisher1;
                break;
            case "광부":
                title = TitleNameManager.Miner1;
                prefix = TitleNameManager.Miner1;
                break;
            case "농부":
                title = TitleNameManager.Farmer1;
                prefix = TitleNameManager.Farmer1;
                break;
            case "나무꾼":
                title = TitleNameManager.WoodCutter1;
                prefix = TitleNameManager.WoodCutter1;
                break;
            case "사냥꾼":
                title = TitleNameManager.Hunter1;
                prefix = TitleNameManager.Hunter1;
                break;
            case "뇌창":
                title = TitleNameManager.LightningCharger;
                prefix = TitleNameManager.LightningCharger;
                break;
            case "약탈자":
                title = TitleNameManager.Looter;
                prefix = TitleNameManager.Looter;
                break;
            default:
                title = "???";
                prefix = "[???]";
        }
        PlayerConfig.set(path_title, title);
        PlayerConfig.set(path_prefix, prefix);
        SavePlayerConfig();
        PlayerScoreboardManager.reloadScboard(player);
    }

    public static String GetTitle(Player player)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".title";
        return PlayerConfig.getString(path);
    }

    public static String GetTitlePrefix(Player player)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".prefix";
        return PlayerConfig.getString(path);
    }

    public static void SetJob(Player player, String job)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".job";
        String jobName;

        switch (job)
        {
            case "운영자":
                jobName = JobNameManager.OperatorName;
                break;
            case "무직":
                jobName = JobNameManager.JobLessName;
                break;
            case "낚시꾼":
                jobName = JobNameManager.FisherName;
                break;
            case "광부":
                jobName = JobNameManager.MinerName;
                break;
            case "농부":
                jobName = JobNameManager.FarmerName;
                break;
            case "나무꾼":
                jobName = JobNameManager.WoodCutterName;
                break;
            case "사냥꾼":
                jobName = JobNameManager.HunterName;
                break;
            case "요리사":
                jobName = JobNameManager.CookName;
                break;
            default:
                jobName = "???";
        }

        PlayerConfig.set(path, jobName);
        SavePlayerConfig();
        PlayerScoreboardManager.reloadScboard(player);
    }

    public static String GetJob(Player player)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".job";
        return PlayerConfig.getString(path);
    }

    public static void SetJobLevel(Player player, Integer lv)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".joblv";
        PlayerConfig.set(path, lv);
    }

    public static Integer GetJobLevel(Player player)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".joblv";
        return PlayerConfig.getInt(path);
    }

    public static void SetGold(Player player, Integer amount)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".gold";
        PlayerConfig.set(path, amount);
        SavePlayerConfig();
        PlayerScoreboardManager.reloadScboard(player);
    }

    public static int GetGold(Player player)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".gold";
        return PlayerConfig.getInt(path);
    }

    public static void AddGold(Player player, Integer amount)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".gold";
        int currGold = PlayerConfig.getInt(path);
        currGold += amount;
        PlayerConfig.set(path, currGold);
        SavePlayerConfig();
        PlayerScoreboardManager.reloadScboard(player);
    }

    public static void UseGold(Player player, Integer amount)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".gold";
        int currGold = PlayerConfig.getInt(path);
        currGold -= amount;
        PlayerConfig.set(path, currGold);
        SavePlayerConfig();
        PlayerScoreboardManager.reloadScboard(player);
    }

    public static void SetCoin(Player player, String coin, Integer amount)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+coin;
        PlayerConfig.set(path, amount);
        SavePlayerConfig();
    }

    public static int GetCoin(Player player, String coin)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+coin;
        if(PlayerConfig.getInt(path) == 0)
        {
            PlayerConfig.set(path, 0);
        }
        return PlayerConfig.getInt(path);
    }

    public static void AddCoin(Player player, String coin, Integer amount)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+coin;
        if(PlayerConfig.getInt(path) == 0)
        {
            PlayerConfig.set(path, 0);
        }
        int currCoin = PlayerConfig.getInt(path);
        currCoin += amount;
        PlayerConfig.set(path, currCoin);

        SavePlayerConfig();
    }

    public static void SellCoin(Player player, String coin, Integer amount)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+coin;
        int currCoin = PlayerConfig.getInt(path);
        currCoin -= amount;
        PlayerConfig.set(path, currCoin);
        SavePlayerConfig();
    }

    public static void SetHidden(Player player, String hiddenName, int num)
    {
        UUID uuid = player.getUniqueId();
        String path = "players." + uuid + "." + hiddenName;
        PlayerConfig.set(path, num);
        SavePlayerConfig();
    }

    public static Integer GetHidden(Player player, String hiddenName)
    {
        UUID uuid = player.getUniqueId();
        String path = "players." + uuid + "." + hiddenName;
        if(!PlayerConfig.contains(path))
        {
            return 0;
        }
        return PlayerConfig.getInt(path);
    }

    private static void SavePlayerConfig()
    {
        TestPlugin.getConfigManager().saveConfig("player");
    }
}
