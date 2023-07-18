package com.habu.testplugin.command;

import com.habu.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CallOperator implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;

        List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
        for(int i = 0; i < playerList.size(); i++)
        {
            if(playerList.get(i).getName().equals("Happy_Kkyulmik"))
            {
                Player operator = playerList.get(i);
                operator.sendMessage( ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " 님이 운영자를 호출했습니다.");
                return false;
            }
        }

        player.sendMessage(ChatColor.RED + "운영자가 부재중입니다.");

        return false;
    }
}
