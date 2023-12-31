package com.habu.testplugin.command.operatorcommand;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SetPlayerGold implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        if(player.isOp())
        {
            if(args.length < 2)
            {
                player.sendMessage(ChatColor.RED + "/goldsetplayer [플레이어이름] [금액]");
            }
            List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
            for(int i = 0; i < playerList.size(); i++)
            {
                if(playerList.get(i).getName().equals(args[0]))
                {
                    Player targetPlayer = playerList.get(i);
                    TestPlugin.db_conn.SetGold(targetPlayer, Integer.parseInt(args[1]));
                    player.sendMessage( ChatColor.AQUA + "성공적으로 반영되었습니다.");
                    return false;
                }
            }
        }
        return false;
    }
}
