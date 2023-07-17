package com.habu.testplugin.event.job;

import com.habu.testplugin.manager.NPCNameManager;
import com.habu.testplugin.shop.selectshop.FisherSelectShop;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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
            if(npcName.equals(NPCNameManager.FisherSelectShopNPCName))
            {
                FisherSelectShop fisherSelectShop = new FisherSelectShop();
                fisherSelectShop.open(player);
            }
        }
    }
}
