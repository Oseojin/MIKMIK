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
                title = ChatColor.AQUA + "운영자" + ChatColor.WHITE;
                prefix = ChatColor.AQUA + "[운영자]" + ChatColor.WHITE;
                break;
            case 0:
                title = "백수";
                prefix = "[백수]";
                break;
            case 1:
                title = ChatColor.AQUA + "물고기" + ChatColor.WHITE;
                prefix = ChatColor.AQUA + "[물고기]" + ChatColor.WHITE;
                break;
            case 2:
                title = ChatColor.YELLOW + "곡괭이" + ChatColor.WHITE;
                prefix = ChatColor.YELLOW + "[곡괭이]" + ChatColor.WHITE;
                break;
            case 3:
                title = ChatColor.GREEN + "밀" + ChatColor.WHITE;
                prefix = ChatColor.GREEN + "[밀]" + ChatColor.WHITE;
                break;
            case 4:
                title = ChatColor.GOLD + "장작" + ChatColor.WHITE;
                prefix = ChatColor.GOLD + "[장작]" + ChatColor.WHITE;
                break;
            case 5:
                title = ChatColor.DARK_GREEN + "활" + ChatColor.WHITE;
                prefix = ChatColor.DARK_GREEN + "[활]" + ChatColor.WHITE;
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
            case 0:
                jobName = ChatColor.WHITE + "[무직]" + ChatColor.WHITE;
                break;
            case 1:
                jobName = ChatColor.AQUA + "[낚시꾼]" + ChatColor.WHITE;
                break;
            case 2:
                jobName = ChatColor.YELLOW + "[광부]" + ChatColor.WHITE;
                break;
            case 3:
                jobName = ChatColor.GREEN + "[농부]" + ChatColor.WHITE;
                break;
            case 4:
                jobName = ChatColor.GOLD + "[나무꾼]" + ChatColor.WHITE;
                break;
            case 5:
                jobName = ChatColor.DARK_GREEN + "[사냥꾼]" + ChatColor.WHITE;
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
