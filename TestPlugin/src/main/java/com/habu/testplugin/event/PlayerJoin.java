package com.habu.testplugin.event;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.MessageManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.manager.PlayerScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

public class PlayerJoin implements Listener
{
    private void setScoreboard(Player player)
    {
        PlayerScoreboardManager.scboard(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID uuid = player.getUniqueId();

        if(!player.hasPlayedBefore()) // 월드 데이터에 플레이어 정보가 없다면
        {
            MessageManager.NewPlayerJoinMessage(event, playerName);

            PlayerManager.SetName(player, playerName);
            PlayerManager.SetGold(player, 1000);
            PlayerManager.SetJob(player, "무직");
            PlayerManager.SetJobLevel(player, 0);
        }
        else // 월드 데이터에 플레이어 정보가 있다면
        {
            // 근데 config 정보에는 없다면 --> config 데이터 소실
            if(PlayerManager.GetName(player) == null)
            {
                // 아쉽지만 초기화...
                PlayerManager.SetName(player, playerName);
                PlayerManager.SetGold(player, 1000);
            }
            if(PlayerManager.GetJob(player) == null)
            {
                PlayerManager.SetJob(player, "무직");
                PlayerManager.SetJobLevel(player, 0);
            }
            MessageManager.PlayerJoinMessage(event, playerName);
        }

        setScoreboard(player);

        TestPlugin.getConfigManager().saveConfig("player");
    }

    @EventHandler
    public void PlayerLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID uuid = player.getUniqueId();

        if(!TestPlugin.getConfigManager().getConfig("whitelist").contains("players."+playerName.toLowerCase()))
        {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "초대받지 못했습니다!");
        }
    }
}