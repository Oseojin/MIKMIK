package com.habu.testplugin.manager;

import com.habu.testplugin.TestPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class MessageManager
{
    private static FileConfiguration MessageConfig = TestPlugin.getConfigManager().getConfig("message");

    public static void NewPlayerJoinMessage(PlayerJoinEvent event, String playerName)
    {
        if(MessageConfig.getBoolean("new-player-join-message.display-message"))
        {
            event.setJoinMessage(TestPlugin.getConfigManager().getConfigColorString("message", "new-player-join-message.message")
                    .replace("{playername}", playerName));
        }
    }

    public static void PlayerJoinMessage(PlayerJoinEvent event, String playerName)
    {
        if(MessageConfig.getBoolean("join-message.display-message"))
        {
            event.setJoinMessage(TestPlugin.getConfigManager().getConfigColorString("message", "join-message.message")
                    .replace("{playername}", playerName));
        }
    }

    public static void PlayerTitleApplication(Player player, String prefix)
    {
        player.setPlayerListName(prefix + " " + player.getName());
    }
}
