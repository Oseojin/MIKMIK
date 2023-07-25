package com.habu.testplugin.command.operatorcommand;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.JobNameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SetPlayerJob implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        if(player.isOp())
        {
            if(args.length < 2)
            {
                player.sendMessage(ChatColor.RED + "/jobsetplayer [플레이어이름] [직업이름]");
            }
            List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
            for(int i = 0; i < playerList.size(); i++)
            {
                if(playerList.get(i).getName().equals(args[0]))
                {
                    Player targetPlayer = playerList.get(i);
                    String jobName = args[1];

                    switch (jobName)
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
                        default:
                            jobName = "???";
                    }

                    TestPlugin.db_conn.SetJob(targetPlayer, jobName);
                    player.sendMessage( ChatColor.AQUA + "성공적으로 반영되었습니다.");
                    return false;
                }
            }
        }
        return false;
    }
}
