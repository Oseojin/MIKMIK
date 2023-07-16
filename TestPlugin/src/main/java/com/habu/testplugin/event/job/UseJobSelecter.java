package com.habu.testplugin.event.job;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UseJobSelecter implements Listener
{

    @EventHandler
    public void UseSelecter(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack useItem = player.getItemInHand();
        String playerJob = ChatColor.stripColor(PlayerManager.GetJob(player));
        if((action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)))
        {
            if(playerJob.equals("[무직]"))
            {
                if(useItem.isSimilar(ItemManager.item_FisherSelecter))
                {
                    PlayerManager.SetJob(player, 1);
                    TestPlugin.getConfigManager().saveConfig("player");
                    player.getInventory().removeItem(useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_MinerSelecter))
                {
                    PlayerManager.SetJob(player, 2);
                    TestPlugin.getConfigManager().saveConfig("player");
                    player.getInventory().removeItem(useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_FarmerSelecter))
                {
                    PlayerManager.SetJob(player, 3);
                    TestPlugin.getConfigManager().saveConfig("player");
                    player.getInventory().removeItem(useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_WoodCutterSelecter))
                {
                    PlayerManager.SetJob(player, 4);
                    TestPlugin.getConfigManager().saveConfig("player");
                    player.getInventory().removeItem(useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_JobInitializer))
                {
                    player.sendMessage("직업 초기화는 직업을 가지고 있는 상태에서만 사용 가능합니다.");
                }
            }
            else
            {
                if(useItem.isSimilar(ItemManager.item_FisherSelecter) || useItem.isSimilar(ItemManager.item_MinerSelecter) || useItem.isSimilar(ItemManager.item_FarmerSelecter) || useItem.isSimilar(ItemManager.item_WoodCutterSelecter))
                {
                    player.sendMessage("직업 선택은 [무직] 상태에서만 가능합니다.");
                }
                else if(useItem.isSimilar(ItemManager.item_JobInitializer))
                {
                    PlayerManager.SetJob(player, 0);
                    TestPlugin.getConfigManager().saveConfig("player");
                    player.getInventory().removeItem(useItem);
                }
            }
        }
    }
}
