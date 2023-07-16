package com.habu.testplugin.command;

import com.habu.testplugin.event.BlockTestEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BlockCheckCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        if(!player.isOp())
            return false;
        if(BlockTestEvent.getSwitch())
        {
            BlockTestEvent.Switch(false);
            sender.sendMessage("turn False");
        }
        else
        {
            BlockTestEvent.Switch(true);
            sender.sendMessage("turn True");
        }
        return false;
    }
}
