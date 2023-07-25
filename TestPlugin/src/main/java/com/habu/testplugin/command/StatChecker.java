package com.habu.testplugin.command;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class StatChecker implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if(!player.isOp())
            return false;

        if(args.length < 1)
        {
            player.sendMessage("/statchecker [플레이어이름]");
            return false;
        }

        List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
        for(int i = 0; i < playerList.size(); i++)
        {
            if(playerList.get(i).getName().equals(args[0]))
            {
                Player targetPlayer = playerList.get(i);
                player.sendMessage("Name: " + TestPlugin.db_conn.GetName(targetPlayer));
                player.sendMessage("Job: " + TestPlugin.db_conn.GetJob(targetPlayer));
                player.sendMessage("Gold: " + TestPlugin.db_conn.GetGold(targetPlayer));
                return false;
            }
        }

        return false;
    }
}
