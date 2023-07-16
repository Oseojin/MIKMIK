package com.habu.testplugin.event.job;

import com.habu.testplugin.shop.FisherSelectShop;
import com.habu.testplugin.shop.MinerSelectShop;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class MinerSelectNPC implements Listener
{
    @EventHandler
    public void ClickNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(entity.getType().equals(EntityType.MINECART_FURNACE))
        {
            String npcName = entity.getName();
            if(npcName.equals(ChatColor.YELLOW + "[광부]" + ChatColor.WHITE + " 인생"))
            {
                MinerSelectShop minerSelectShop = new MinerSelectShop();
                minerSelectShop.open(player);
            }
        }
    }
}
