package com.habu.testplugin.event.job;

import com.habu.testplugin.shop.FarmerSelectShop;
import com.habu.testplugin.shop.FisherSelectShop;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class FarmerSelectNPC implements Listener
{
    @EventHandler
    public void ClickNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(entity.getType().equals(EntityType.COW))
        {
            String npcName = entity.getName();
            if(npcName.equals(ChatColor.GREEN + "[농부]" + ChatColor.WHITE + " 인생"))
            {
                FarmerSelectShop farmerSelectShop = new FarmerSelectShop();
                farmerSelectShop.open(player);
            }
        }
    }
}
