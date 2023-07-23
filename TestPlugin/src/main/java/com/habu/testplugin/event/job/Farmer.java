package com.habu.testplugin.event.job;

import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Farmer implements Listener
{
    Random random = new Random();
    List<Material> allCrop = new ArrayList<>(Arrays.asList(
            Material.WHEAT,
            Material.CARROTS,
            Material.POTATOES,
            Material.BEETROOTS,
            Material.MELON,
            Material.PUMPKIN,
            Material.COCOA,
            Material.NETHER_WART
    ));

    List<Material> seedList = new ArrayList<>(Arrays.asList(
            Material.WHEAT_SEEDS,
            Material.CARROT,
            Material.POTATO,
            Material.BEETROOT_SEEDS,
            Material.MELON_SEEDS,
            Material.PUMPKIN_SEEDS,
            Material.COCOA_BEANS,
            Material.NETHER_WART
    ));

    private Integer RandomCrop()
    {
        int randNum = random.nextInt(100);

        // S >>> A > B > C > D > E // 등급
        // 10>>> 5 > 4 > 3 > 2 > 1 // 획득 수량
        // 1 >>> 5 > 10> 15> 25> 44// 단일 확률
        // 99    94  84  69  44  0 // 실 구현
        int rank = 0;

        if(randNum >= 99)
        {
            rank = 4;
        }
        else if (randNum >= 94)
        {
            rank = 3;
        }
        else if (randNum >= 84)
        {
            rank = 2;
        }
        else if (randNum >= 69)
        {
            rank = 2;
        }
        else if (randNum >= 44)
        {
            rank = 2;
        }
        else
        {
            rank = 2;
        }

        return rank;
    }

    @EventHandler
    public void FarmerHarvest(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        ItemStack playerTool = player.getItemInHand();
        Block block = event.getBlock();

        String playerJob = PlayerManager.GetJob(player);

        if(playerJob.equals(JobNameManager.FarmerName))
        {
            if(allCrop.contains(block.getType()))
            {
                for(int x = -1; x < 2; x++)
                {
                    for(int z = -1; z < 2; z++)
                    {
                        Location blockLoc = block.getLocation();
                        blockLoc.add(x, 0, z);
                        Block nearBlock = block.getWorld().getBlockAt(blockLoc);
                        if(allCrop.contains(nearBlock.getType()))
                        {
                            if(nearBlock.getBlockData() instanceof Ageable)
                            {
                                Ageable blockAge = (Ageable) nearBlock.getBlockData();
                                if(blockAge.getAge() < blockAge.getMaximumAge())
                                {
                                    continue;
                                }
                            }
                            nearBlock.breakNaturally(playerTool);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void FarmerSeed(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        Inventory playerInv = player.getInventory();
        ItemStack playerTool = player.getItemInHand();
        Block block = event.getBlock();

        String playerJob = PlayerManager.GetJob(player);

        if(playerJob.equals(JobNameManager.FarmerName))
        {
            if(seedList.contains(playerTool.getType()))
            {
                playerTool.setAmount(playerTool.getAmount() - 1);
                for(int x = -1; x < 2; x++)
                {
                    for(int z = -1; z < 2; z++)
                    {
                        Location blockLoc = block.getLocation();
                        blockLoc.add(x, 0, z);
                        Block nearBlock = block.getWorld().getBlockAt(blockLoc);
                        if(nearBlock.canPlace(block.getBlockData()) && !allCrop.contains(nearBlock.getType()))
                        {
                            int playerIndex = playerInv.first(playerTool.getType());
                            if(playerIndex == -1)
                            {
                                return;
                            }
                            ItemStack invItem = playerInv.getItem(playerIndex);
                            invItem.setAmount(invItem.getAmount() - 1);
                            nearBlock.setType(block.getType());
                        }
                    }
                }
            }
        }
    }
}
