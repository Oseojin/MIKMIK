package com.habu.testplugin.Hidden;

import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.NPCNameManager;
import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class LightningNPC implements Listener
{
    @EventHandler
    public void InteractLightningNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(PlayerManager.GetJob(player).equals(JobNameManager.OperatorName))
        {
            return;
        }

        if(entity.getType().equals(EntityType.VILLAGER))
        {
            String npcName = entity.getName();
            if(npcName.equals(NPCNameManager.LightningNPCName))
            {
                LightningChargerManager.FINDHIDDEN(player);
            }
        }
    }
}