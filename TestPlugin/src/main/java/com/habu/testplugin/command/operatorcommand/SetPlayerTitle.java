package com.habu.testplugin.command.operatorcommand;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.TitleNameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SetPlayerTitle implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        if(player.isOp())
        {
            if(args.length < 2)
            {
                player.sendMessage(ChatColor.RED + "/titlesetplayer [플레이어이름] [칭호이름]");
            }
            List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
            for(int i = 0; i < playerList.size(); i++)
            {
                if(playerList.get(i).getName().equals(args[0]))
                {
                    Player targetPlayer = playerList.get(i);
                    String title = args[1];

                    switch (title)
                    {
                        case "운영자":
                            title = TitleNameManager.Operator;
                            break;
                        case "무직":
                            title = TitleNameManager.JobLess;
                            break;
                        case "낚시꾼":
                            title = TitleNameManager.Fisher;
                            break;
                        case "광부":
                            title = TitleNameManager.Miner;
                            break;
                        case "농부":
                            title = TitleNameManager.Farmer;
                            break;
                        case "나무꾼":
                            title = TitleNameManager.WoodCutter;
                            break;
                        case "사냥꾼":
                            title = TitleNameManager.Hunter;
                            break;
                        default:
                            title = "???";
                    }

                    TestPlugin.db_conn.SetTitle(targetPlayer, title);
                    player.sendMessage( ChatColor.AQUA + "성공적으로 반영되었습니다.");
                    return false;
                }
            }
        }
        return false;
    }
}
