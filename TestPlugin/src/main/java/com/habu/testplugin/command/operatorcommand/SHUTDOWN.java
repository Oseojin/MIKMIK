package com.habu.testplugin.command.operatorcommand;

import com.habu.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SHUTDOWN implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        if(player.isOp())
        {
            if(args.length < 1)
            {
                player.sendMessage(ChatColor.RED + "/shutdown [시간]");
                return false;
            }
            new BukkitRunnable()
            {
                int count = Integer.parseInt(args[0]);
                @Override
                public void run()
                {
                    if(count <= 0)
                    {
                        Bukkit.shutdown();
                    }
                    else
                    {
                        Bukkit.broadcastMessage(ChatColor.RED + "" + count + "초 뒤 서버가 꺼집니다.");
                        count--;
                    }
                }
            }.runTaskTimer(TestPlugin.getPlugin(), 0L, 20L);
        }
        return false;
    }
}
