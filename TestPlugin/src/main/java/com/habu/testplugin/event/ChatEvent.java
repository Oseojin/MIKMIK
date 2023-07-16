package com.habu.testplugin.event;

import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatEvent implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID uuid = player.getUniqueId();

        String prefix = PlayerManager.GetTitlePrefix(player);
        event.setFormat(prefix + " " + playerName + ": " +  event.getMessage());
    }
}
