package com.habu.testplugin.event.job;

import com.habu.testplugin.shop.FisherSelectShop;
import com.habu.testplugin.shop.WoodCutterSelectShop;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.material.Wood;

public class WoodCutterSelectNPC implements Listener
{
    @EventHandler
    public void ClickNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(entity.getType().equals(EntityType.PANDA))
        {
            String npcName = entity.getName();
            if(npcName.equals(ChatColor.GOLD + "[나무꾼]" + ChatColor.WHITE + " 인생"))
            {
                WoodCutterSelectShop woodCutterSelectShop = new WoodCutterSelectShop();
                woodCutterSelectShop.open(player);
            }
        }
    }
}
