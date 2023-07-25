package com.habu.testplugin.event.job;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.TitleNameManager;
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

    List<ItemStack> jobSelecters = new ArrayList<>(Arrays.asList(
            ItemManager.item_FisherSelecter,
            ItemManager.item_MinerSelecter,
            ItemManager.item_FarmerSelecter,
            ItemManager.item_WoodCutterSelecter,
            ItemManager.item_HunterSelecter
    ));

    private void configJob(Player player, String jobName, String titleName, ItemStack useItem)
    {
        TestPlugin.db_conn.SetJob(player, jobName);
        TestPlugin.db_conn.SetTitle(player, titleName);
        int amount = useItem.getAmount() - 1;
        useItem.setAmount(amount);
    }

    @EventHandler
    public void UseSelecter(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack useItem = player.getItemInHand();
        String playerJob = TestPlugin.db_conn.GetJob(player);
        if((action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)))
        {
            if(playerJob.equals(JobNameManager.JobLessName))
            {
                if(useItem.isSimilar(ItemManager.item_FisherSelecter))
                {
                    configJob(player, JobNameManager.FisherName, TitleNameManager.Fisher, useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_MinerSelecter))
                {
                    configJob(player, JobNameManager.MinerName, TitleNameManager.Miner, useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_FarmerSelecter))
                {
                    configJob(player, JobNameManager.FarmerName, TitleNameManager.Farmer, useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_WoodCutterSelecter))
                {
                    configJob(player, JobNameManager.WoodCutterName, TitleNameManager.WoodCutter, useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_HunterSelecter))
                {
                    configJob(player, JobNameManager.HunterName, TitleNameManager.Hunter, useItem);
                }
                else if(useItem.isSimilar(ItemManager.item_JobInitializer))
                {
                    player.sendMessage("직업 초기화는 직업을 가지고 있는 상태에서만 사용 가능합니다.");
                }
            }
            else
            {
                ItemStack copyUseItem = useItem.clone();
                copyUseItem.setAmount(1);
                if(jobSelecters.contains(copyUseItem))
                {
                    player.sendMessage("직업 선택은 [무직] 상태에서만 가능합니다.");
                }
                else if(useItem.isSimilar(ItemManager.item_JobInitializer))
                {
                    configJob(player, JobNameManager.JobLessName, TitleNameManager.JobLess, useItem);
                }
            }
        }
    }
}
