package com.habu.testplugin.event;

import com.habu.testplugin.TestPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String title = TestPlugin.db_conn.GetTitle(player);
        event.setFormat(title + player.getName() + ": " + event.getMessage());
    }
}
