package com.habu.testplugin.command.operatorcommand;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.PlayerScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DBLOADPLAYER implements CommandExecutor
{
    private void setScoreboard(Player player)
    {
        PlayerScoreboardManager.scboard(player);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(!sender.isOp())
            return false;
        sender.sendMessage("dbLoad");
        List<Player> onlinePlayer = (List<Player>) Bukkit.getOnlinePlayers();
        for(int i = 0; i < onlinePlayer.size(); i++)
        {
            Player player = onlinePlayer.get(i);

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

        return false;
    }
}
