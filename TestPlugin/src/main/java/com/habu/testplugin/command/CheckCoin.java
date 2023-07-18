package com.habu.testplugin.command;

import com.habu.testplugin.manager.CoinManager;
import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CheckCoin implements CommandExecutor
{
    private String PungsanDogCoin = ".pungsandog_coin";
    private String MoleCoin = ".mole_coin";
    private String BeetCoin = ".beet_coin";
    private String KimchiCoin = ".kimchi_coin";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        String path = "";
        player.sendMessage(ChatColor.AQUA + "[코인 보유 목록]");
        path = "players." + uuid + PungsanDogCoin;
        player.sendMessage( CoinManager.PungsanDogCoinName + ChatColor.WHITE + ": " + PlayerManager.GetCoin(player, PungsanDogCoin));
        path = "players." + uuid + MoleCoin;
        player.sendMessage( CoinManager.MoleCoinName+ ChatColor.WHITE + ": " + PlayerManager.GetCoin(player, MoleCoin));
        path = "players." + uuid + BeetCoin;
        player.sendMessage( CoinManager.BeetCoinName+ ChatColor.WHITE + ": " + PlayerManager.GetCoin(player, BeetCoin));
        path = "players." + uuid + KimchiCoin;
        player.sendMessage( CoinManager.KimchiCoinName+ ChatColor.WHITE + ": " + PlayerManager.GetCoin(player, KimchiCoin));


        return false;
    }
}
