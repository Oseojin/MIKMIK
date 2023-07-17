package com.habu.testplugin.event.job;

import com.habu.testplugin.manager.NPCNameManager;
import com.habu.testplugin.shop.selectshop.WoodCutterSelectShop;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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
            if(npcName.equals(NPCNameManager.WoodCutterSelectShopNPCName))
            {
                WoodCutterSelectShop woodCutterSelectShop = new WoodCutterSelectShop();
                woodCutterSelectShop.open(player);
            }
        }
    }
}
