package com.habu.testplugin.event;

import com.habu.testplugin.manager.NPCNameManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.shop.*;
import com.habu.testplugin.shop.jobshop.FarmerShop;
import com.habu.testplugin.shop.jobshop.FisherShop;
import com.habu.testplugin.shop.jobshop.MinerShop;
import com.habu.testplugin.shop.jobshop.WoodCutterShop;
import com.habu.testplugin.shop.randomshop.RandomSpawnEgg;
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
            if(npcName.equals(NPCNameManager.FisherShopNPCName))
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
            else if(npcName.equals(NPCNameManager.FarmerShopNPCName))
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
            else if(npcName.equals(NPCNameManager.MinerShopNPCName))
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
            else if(npcName.equals(NPCNameManager.WoodCutterShopNPCName))
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
    }
}
