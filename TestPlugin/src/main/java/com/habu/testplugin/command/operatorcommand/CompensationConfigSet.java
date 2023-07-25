package com.habu.testplugin.command.operatorcommand;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.JobNameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CompensationConfigSet implements CommandExecutor
{
    FileConfiguration compensation = TestPlugin.getConfigManager().getConfig("compensation");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        if(!player.isOp())
        {
            player.sendMessage("관리자 전용 명령어입니다.");
        }
        if(args.length < 3)
        {
            player.sendMessage("/compensationset [플레이어이름] [직업] [골드]");
        }

        String playerName = args[0];
        String jobName = args[1];
        Integer gold = Integer.parseInt(args[2]);
        gold = gold + (int)Math.round((double)gold * 0.2);

        compensation.set(playerName, "");
        compensation.set(playerName + ".job", jobName);
        compensation.set(playerName + ".gold", gold);
        compensation.set(playerName + ".isreceive", false);

        TestPlugin.getConfigManager().saveConfig("compensation");

        player.sendMessage(ChatColor.AQUA + "성공적으로 저장되었습니다!");
        return false;
    }
}
