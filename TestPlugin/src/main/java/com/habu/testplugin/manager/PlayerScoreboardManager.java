package com.habu.testplugin.manager;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class PlayerScoreboardManager
{
    private static Scoreboard board;
    private static Objective obj;
    private static Score score;

    public static void scboard(Player player)
    {
        String playerName = player.getName();
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        board = sm.getNewScoreboard();
        obj = board.registerNewObjective("playerScoreboard", playerName);
        score = obj.getScore(ChatColor.DARK_GRAY + "-----------------");
        score.setScore(5);
        score = obj.getScore(TestPlugin.db_conn.GetName(player));
        score.setScore(4);
        score = obj.getScore(ChatColor.GREEN + "직업: " + TestPlugin.db_conn.GetJob(player));
        score.setScore(3);
        score = obj.getScore(ChatColor.AQUA + "칭호: " + TestPlugin.db_conn.GetTitle(player));
        score.setScore(2);
        score = obj.getScore(ChatColor.GOLD + "소지금: " + TestPlugin.db_conn.GetGold(player));
        score.setScore(1);
        score = obj.getScore(ChatColor.DARK_GRAY + "=================");
        score.setScore(0);
        obj.setDisplayName(ChatColor.AQUA + "MIKMIK SERVER 2");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }

    public static void reloadScboard(Player player)
    {
        String playerName = player.getName();
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        board = sm.getNewScoreboard();
        obj = board.registerNewObjective("playerScoreboard", playerName);
        score = obj.getScore(ChatColor.DARK_GRAY + "-----------------");
        score.setScore(5);
        score = obj.getScore(TestPlugin.db_conn.GetName(player));
        score.setScore(4);
        score = obj.getScore(ChatColor.GREEN + "직업: " + TestPlugin.db_conn.GetJob(player));
        score.setScore(3);
        score = obj.getScore(ChatColor.AQUA + "칭호: " + TestPlugin.db_conn.GetTitle(player));
        score.setScore(2);
        score = obj.getScore(ChatColor.GOLD + "소지금: " + TestPlugin.db_conn.GetGold(player));
        score.setScore(1);
        score = obj.getScore(ChatColor.DARK_GRAY + "=================");
        score.setScore(0);
        obj.setDisplayName(ChatColor.AQUA + "MIKMIK SERVER 2");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }
}