package com.habu.testplugin.event.job;

import com.habu.testplugin.shop.FisherSelectShop;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class FisherSelectNPC implements Listener
{
    @EventHandler
    public void ClickNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(entity.getType().equals(EntityType.SALMON))
        {
            String npcName = entity.getName();
            if(npcName.equals(ChatColor.AQUA + "[낚시꾼]" + ChatColor.WHITE + " 인생"))
            {
                FisherSelectShop fisherSelectShop = new FisherSelectShop();
                fisherSelectShop.open(player);
            }
        }
    }
}
