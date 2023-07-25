package com.habu.testplugin.event;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.PlayerScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

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
        if(TestPlugin.db_conn.insertMember(player) == 0)
        {
            TestPlugin.db_conn.db_PlayerInfo(player);
            setScoreboard(player);
            String title = TestPlugin.db_conn.GetTitle(player);
            player.setPlayerListName(title + player.getName());
        }
        else
        {
            player.kickPlayer("데이터베이스에서 정보를 로드중 오류가 발생 했습니다. " + TestPlugin.db_conn.insertMember(player));
        }
    }

    @EventHandler
    public void PlayerLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if(!TestPlugin.getConfigManager().getConfig("whitelist").contains("players."+playerName.toLowerCase()))
        {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "초대받지 못했습니다!");
        }
    }
}