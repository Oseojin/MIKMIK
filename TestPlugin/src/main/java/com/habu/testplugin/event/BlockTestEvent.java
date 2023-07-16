package com.habu.testplugin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockTestEvent implements Listener
{
    private static boolean isCheck = false;
    @EventHandler
    public void CheckBlockType(BlockBreakEvent event)
    {
        if(!isCheck)
            return;
        Player player = event.getPlayer();
        player.sendMessage(event.getBlock().getType().toString());
    }

    public static boolean getSwitch()
    {
        return isCheck;
    }

    public static void Switch(boolean _isCheck)
    {
        isCheck = _isCheck;
    }
}
