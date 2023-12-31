package com.habu.testplugin.event;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.NPCNameManager;
import com.habu.testplugin.shop.GeneralShop;
import com.habu.testplugin.shop.jobshop.FarmerShop;
import com.habu.testplugin.shop.jobshop.FisherShop;
import com.habu.testplugin.shop.jobshop.MinerShop;
import com.habu.testplugin.shop.jobshop.WoodCutterShop;
import com.habu.testplugin.shop.randomshop.RandomSpawnEgg;
import com.habu.testplugin.shop.selectshop.*;
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
            String playerJob = TestPlugin.db_conn.GetJob(player);
            if(npcName.equals(NPCNameManager.FisherShopNPCName))
            {
                if(playerJob.equals(JobNameManager.FisherName) || playerJob.equals(JobNameManager.OperatorName))
                {
                    FisherShop inv = new FisherShop();
                    inv.open(player);
                }
                else
                {
                    player.sendMessage(npcName + ChatColor.WHITE + "은" + JobNameManager.FisherName + "만 이용할 수 있습니다.");
                }
            }
            else if(npcName.equals(NPCNameManager.MinerShopNPCName))
            {
                if(playerJob.equals(JobNameManager.MinerName) || playerJob.equals(JobNameManager.OperatorName))
                {
                    MinerShop inv = new MinerShop();
                    inv.open(player);
                }
                else
                {
                    player.sendMessage(npcName + ChatColor.WHITE + "은" + JobNameManager.MinerName + "만 이용할 수 있습니다.");
                }
            }
            else if(npcName.equals(NPCNameManager.FarmerShopNPCName))
            {
                if(playerJob.equals(JobNameManager.FarmerName) || playerJob.equals(JobNameManager.OperatorName))
                {
                    FarmerShop inv = new FarmerShop();
                    inv.open(player);
                }
                else
                {
                    player.sendMessage(npcName + ChatColor.WHITE + "은" + JobNameManager.FarmerName + "만 이용할 수 있습니다.");
                }
            }
            else if(npcName.equals(NPCNameManager.WoodCutterShopNPCName))
            {
                if(playerJob.equals(JobNameManager.WoodCutterName) || playerJob.equals(JobNameManager.OperatorName))
                {
                    WoodCutterShop inv = new WoodCutterShop();
                    inv.open(player);
                }
                else
                {
                    player.sendMessage(npcName + ChatColor.WHITE + "은" + JobNameManager.WoodCutterName + "만 이용할 수 있습니다.");
                }
            }
            else if(npcName.equals(NPCNameManager.GeneralShopNPCName))
            {
                GeneralShop inv = new GeneralShop();
                inv.open(player);
            }
            else if(npcName.equals(NPCNameManager.RandomSpawnEggShopNPCName))
            {
                RandomSpawnEgg inv = new RandomSpawnEgg();
                inv.open(player);
            }
        }
        else if(entity.getType().equals(EntityType.SALMON))
        {
            String npcName = entity.getName();
            if(npcName.equals(NPCNameManager.FisherSelectShopNPCName))
            {
                FisherSelectShop fisherSelectShop = new FisherSelectShop();
                fisherSelectShop.open(player);
            }
        }
        else if(entity.getType().equals(EntityType.IRON_GOLEM))
        {
            String npcName = entity.getName();
            if(npcName.equals(NPCNameManager.MinerSelectShopNPCName))
            {
                MinerSelectShop minerSelectShop = new MinerSelectShop();
                minerSelectShop.open(player);
            }
        }
        else if(entity.getType().equals(EntityType.COW))
        {
            String npcName = entity.getName();
            if(npcName.equals(NPCNameManager.FarmerSelectShopNPCName))
            {
                FarmerSelectShop farmerSelectShop = new FarmerSelectShop();
                farmerSelectShop.open(player);
            }
        }
        else if(entity.getType().equals(EntityType.PANDA))
        {
            String npcName = entity.getName();
            if(npcName.equals(NPCNameManager.WoodCutterSelectShopNPCName))
            {
                WoodCutterSelectShop woodCutterSelectShop = new WoodCutterSelectShop();
                woodCutterSelectShop.open(player);
            }
        }
        else if(entity.getType().equals(EntityType.FOX))
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
