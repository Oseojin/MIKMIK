package com.habu.testplugin.manager;

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
        score.setScore(4);
        score = obj.getScore(PlayerManager.GetName(player));
        score.setScore(3);
        score = obj.getScore(ChatColor.GREEN + "직업 : " + PlayerManager.GetJob(player));
        score.setScore(2);
        score = obj.getScore(ChatColor.GOLD + "소지금 : " + PlayerManager.GetGold(player));
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
        score.setScore(4);
        score = obj.getScore(PlayerManager.GetName(player));
        score.setScore(3);
        score = obj.getScore(ChatColor.GREEN + "직업 : " + PlayerManager.GetJob(player));
        score.setScore(2);
        score = obj.getScore(ChatColor.GOLD + "소지금 : " + PlayerManager.GetGold(player));
        score.setScore(1);
        score = obj.getScore(ChatColor.DARK_GRAY + "=================");
        score.setScore(0);
        obj.setDisplayName(ChatColor.AQUA + "MIKMIK SERVER 2");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }
}