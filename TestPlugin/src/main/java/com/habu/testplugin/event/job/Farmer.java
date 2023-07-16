package com.habu.testplugin.event.job;

import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.ChatColor;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Farmer implements Listener
{
    Random random = new Random();
    HashMap<Material, Material> allCrop = new HashMap<Material, Material>()
    {{
        put(Material.WHEAT, Material.WHEAT);
        put(Material.CARROTS, Material.CARROT);
        put(Material.POTATOES, Material.POTATO);
        put(Material.BEETROOTS, Material.BEETROOT);
        put(Material.MELON, Material.MELON_SLICE);
        put(Material.PUMPKIN, Material.PUMPKIN);
        put(Material.SWEET_BERRY_BUSH, Material.SWEET_BERRIES);
        put(Material.COCOA, Material.COCOA_BEANS);
        put(Material.NETHER_WART, Material.NETHER_WART);
    }};

    List<Material> ageableCrop = new ArrayList<>(Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.BEETROOTS, Material.COCOA, Material.NETHER_WART));
    List<Material> interactableCrop = new ArrayList<>(Arrays.asList(Material.SWEET_BERRY_BUSH));

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
            rank = 7;
        }
        else if (randNum >= 94)
        {
            rank = 5;
        }
        else if (randNum >= 84)
        {
            rank = 4;
        }
        else if (randNum >= 69)
        {
            rank = 3;
        }
        else if (randNum >= 44)
        {
            rank = 2;
        }
        else
        {
            rank = 1;
        }

        return rank;
    }

    @EventHandler
    public void FarmerDropItem(BlockDropItemEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        List<Item> dropItem = event.getItems();
        String playerJob = ChatColor.stripColor(PlayerManager.GetJob(player));

        if(playerJob.equals("[농부]"))
        {
            for(int index = 0; index < dropItem.size(); index++)
            {
                ItemStack dropItemStack = dropItem.get(index).getItemStack();
                if(allCrop.values().contains(dropItemStack.getType()))
                {
                    int randNum = RandomCrop();
                    int amount = dropItemStack.getAmount() + randNum;
                    dropItemStack.setAmount(amount);
                }
            }
        }
    }

    @EventHandler
    public void FarmerHarvestBlockInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
        UUID uuid = player.getUniqueId();
        Block block = event.getClickedBlock();
        String playerJob = ChatColor.stripColor(PlayerManager.GetJob(player));

        if(playerJob.equals("[농부]"))
        {
            if(action.equals(Action.RIGHT_CLICK_BLOCK) && interactableCrop.contains(block.getType()))
            {
                if (block.getBlockData() instanceof Ageable)
                {
                    Ageable ab = (Ageable) block.getBlockData();
                    if(ab.getAge() < ab.getMaximumAge() && !player.getItemInHand().getType().equals(Material.BONE_MEAL))
                    {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void FarmerHarvestBlock(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Block block = event.getBlock();
        String playerJob = ChatColor.stripColor(PlayerManager.GetJob(player));

        if(playerJob.equals("[농부]"))
        {

            if(ageableCrop.contains(block.getType()))
            {
                if(block.getBlockData() instanceof Ageable)
                {
                    Ageable ab = (Ageable) block.getBlockData();
                    if(ab.getAge() < ab.getMaximumAge())
                    {
                        event.setDropItems(false);
                    }
                }
            }
            else if (interactableCrop.contains(block.getType()))
            {
                event.setDropItems(false);
            }
        }
    }
    
    // 플레이어 한테 아이템 주는 함수
}
