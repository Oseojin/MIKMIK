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

    public static void SetTitle(Player player, Integer titleNum)
    {
        UUID uuid = player.getUniqueId();
        String title = "";
        String prefix = "";
        String path_title = "players."+uuid+".title";
        String path_prefix = "players."+uuid+".prefix";

        switch (titleNum)
        {
            case -1:
                title = TitleNameManager.Operator;
                prefix = TitleNameManager.Operator;
                break;
            case 0:
                title = TitleNameManager.JobLess;
                prefix = TitleNameManager.JobLess;
                break;
            case 1:
                title = TitleNameManager.Fisher1;
                prefix = TitleNameManager.Fisher1;
                break;
            case 2:
                title = TitleNameManager.Miner1;
                prefix = TitleNameManager.Miner1;
                break;
            case 3:
                title = TitleNameManager.Farmer1;
                prefix = TitleNameManager.Farmer1;
                break;
            case 4:
                title = TitleNameManager.WoodCutter1;
                prefix = TitleNameManager.WoodCutter1;
                break;
            case 5:
                title = TitleNameManager.Hunter1;
                prefix = TitleNameManager.Hunter1;
                break;
            default:
                title = "???";
                prefix = "[???]";
        }
        PlayerConfig.set(path_title, title);
        PlayerConfig.set(path_prefix, prefix);
        SavePlayerConfig();
        PlayerScoreboardManager.reloadScboard(player);
        MessageManager.PlayerTitleApplication(player, PlayerManager.GetTitlePrefix(player));
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

    public static void SetJob(Player player, Integer jobNum)
    {
        UUID uuid = player.getUniqueId();
        String path = "players."+uuid+".job";
        String jobName;

        switch (jobNum)
        {
            case -1:
                jobName = JobNameManager.OperatorName;
                break;
            case 0:
                jobName = JobNameManager.JobLessName;
                break;
            case 1:
                jobName = JobNameManager.FisherName;
                break;
            case 2:
                jobName = JobNameManager.MinerName;
                break;
            case 3:
                jobName = JobNameManager.FarmerName;
                break;
            case 4:
                jobName = JobNameManager.WoodCutterName;
                break;
            case 5:
                jobName = JobNameManager.HunterName;
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

    private static void SavePlayerConfig()
    {
        TestPlugin.getConfigManager().saveConfig("player");
    }
}
