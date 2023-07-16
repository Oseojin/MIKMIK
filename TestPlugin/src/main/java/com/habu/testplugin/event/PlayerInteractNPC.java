package com.habu.testplugin.event;

import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.shop.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractNPC implements Listener
{
    @EventHandler
    public void PlayerClickShopNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(entity.getType().equals(EntityType.VILLAGER))
        {
            String npcName = entity.getName();
            String playerJob = ChatColor.stripColor(PlayerManager.GetJob(player));
            if(npcName.equals(ChatColor.AQUA + "바다 상점"))
            {
                if(playerJob.equals("[낚시꾼]"))
                {
                    FisherShop inv = new FisherShop();
                    inv.open(player);
                }
                else
                {
                    player.sendMessage(npcName + ChatColor.WHITE + "은" + ChatColor.AQUA + " [낚시꾼]" + ChatColor.WHITE + "만 이용할 수 있습니다.");
                }
            }
            else if(npcName.equals(ChatColor.GREEN + "작물 상점"))
            {
                if(playerJob.equals("[농부]"))
                {
                    FarmerShop inv = new FarmerShop();
                    inv.open(player);
                }
                else
                {
                    player.sendMessage(npcName + ChatColor.WHITE + "은" + ChatColor.GREEN + " [농부]" + ChatColor.WHITE + "만 이용할 수 있습니다.");
                }
            }
            else if(npcName.equals(ChatColor.YELLOW + "광물 상점"))
            {
                if(playerJob.equals("[광부]"))
                {
                    MinerShop inv = new MinerShop();
                    inv.open(player);
                }
                else
                {
                    player.sendMessage(npcName + ChatColor.WHITE + "은" + ChatColor.YELLOW + " [광부]" + ChatColor.WHITE + "만 이용할 수 있습니다.");
                }
            }
            else if(npcName.equals(ChatColor.GOLD + "나무 상점"))
            {
                if(playerJob.equals("[나무꾼]"))
                {
                    WoodCutterShop inv = new WoodCutterShop();
                    inv.open(player);
                }
                else
                {
                    player.sendMessage(npcName + ChatColor.WHITE + "은" + ChatColor.GOLD + " [나무꾼]" + ChatColor.WHITE + "만 이용할 수 있습니다.");
                }
            }
            else if(npcName.equals(ChatColor.WHITE + "잡화 상점"))
            {
                GeneralShop inv = new GeneralShop();
                inv.open(player);
            }
        }
    }
}
