package com.habu.testplugin.event.job;

import com.habu.testplugin.manager.NPCNameManager;
import com.habu.testplugin.shop.selectshop.HunterSelectShop;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class HunterSelectNPC implements Listener
{
    @EventHandler
    public void ClickNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(entity.getType().equals(EntityType.FOX))
        {
            String npcName = entity.getName();
            if(npcName.equals(NPCNameManager.HunterSelectShopNPCName))
            {
                HunterSelectShop hunterSelectShop = new HunterSelectShop();
                hunterSelectShop.open(player);
            }
        }
    }
}
