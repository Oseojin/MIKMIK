package com.habu.testplugin.event.job;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.JobNameManager;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Miner implements Listener
{
    Random random = new Random();
    /*HashMap<Material, String> mineral = new HashMap<Material, String>()
    {{
        put(Material.COAL, "customitems:unknown_coal");
        put(Material.RAW_COPPER, "customitems:unknown_copper");
        put(Material.RAW_IRON, "customitems:unknown_iron");
        put(Material.RAW_GOLD, "customitems:unknown_gold");
        put(Material.LAPIS_LAZULI, "customitems:unknown_lapis_lazuli");
        put(Material.REDSTONE, "customitems:unknown_redstone");
        put(Material.DIAMOND, "customitems:unknown_diamond");
        put(Material.EMERALD, "customitems:unknown_emerald");
        put(Material.QUARTZ, "customitems:unknown_quartz");
        put(Material.GOLD_NUGGET, "customitems:unknown_gold_nugget");
        put(Material.AMETHYST_SHARD, "customitems:unknown_amethyst_shard");
    }};*/

    HashMap<Material, String> mineral = new HashMap<Material, String>()
    {{
        put(Material.COAL, "customitems:coal_");
        put(Material.RAW_COPPER, "customitems:raw_copper_");
        put(Material.RAW_IRON, "customitems:raw_iron_");
        put(Material.RAW_GOLD, "customitems:raw_gold_");
        put(Material.LAPIS_LAZULI, "customitems:lapis_lazuli_");
        put(Material.REDSTONE, "customitems:redstone_");
        put(Material.DIAMOND, "customitems:diamond_");
        put(Material.EMERALD, "customitems:emerald_");
        put(Material.QUARTZ, "customitems:quartz_");
        put(Material.GOLD_NUGGET, "customitems:gold_nugget_");
        put(Material.AMETHYST_SHARD, "customitems:amethyst_shard_");
    }};

    HashMap<Player, Boolean> breakfurnace = new HashMap<Player, Boolean>();

    private String RandomMineral()
    {
        int randNum = random.nextInt(100);

        // S >>> A > B > C > D > E // 등급
        // 10>>> 5 > 4 > 3 > 2 > 1 // 획득 수량
        // 1 >>> 5 > 10> 15> 25> 44// 단일 확률
        // 99    94  84  69  44  0 // 실 구현
        String rank = "";

        if(randNum >= 99)
        {
            rank = "s";
        }
        else if (randNum >= 94)
        {
            rank = "a";
        }
        else if (randNum >= 84)
        {
            rank = "b";
        }
        else if (randNum >= 69)
        {
            rank = "c";
        }
        else if (randNum >= 44)
        {
            rank = "d";
        }
        else
        {
            rank = "e";
        }

        return rank;
    }

    @EventHandler
    public void MinerBreakFurnace(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        if(TestPlugin.db_conn.GetJob(player).equals(JobNameManager.MinerName))
        {
            if(event.getBlock().getType().equals(Material.FURNACE) || event.getBlock().getType().equals(Material.BLAST_FURNACE))
            {
                breakfurnace.put(player, true);
            }
        }
    }

    /*@EventHandler
    public void MinerFurnace(FurnaceBurnEvent event)
    {
        Bukkit.broadcastMessage("Burn");
    }*/

    /*@EventHandler
    public void MinerDropBlock(BlockDropItemEvent event)
    {
        Player player = event.getPlayer();
        List<Item> dropItem = event.getItems();

        if(TestPlugin.db_conn.GetJob(player).equals(JobNameManager.MinerName))
        {
            for(int index = 0; index < dropItem.size(); index++)
            {
                ItemStack dropItemStack = dropItem.get(index).getItemStack();
                if(mineral.containsKey(dropItemStack.getType()))
                {
                    CustomStack stack = CustomStack.getInstance(mineral.get(dropItemStack.getType()));
                    ItemStack itemStack = stack.getItemStack();
                    itemStack.setAmount(dropItemStack.getAmount());
                    dropItem.get(index).setItemStack(itemStack);
                }
            }
        }
    }*/


    @EventHandler
    public void MinerDropBlock(BlockDropItemEvent event)
    {
        Player player = event.getPlayer();
        List<Item> dropItem = event.getItems();

        String playerJob = player_db_connect.getInstance().GetJob(player);
        if(playerJob.equals(JobNameManager.MinerName) && !breakfurnace.containsKey(player))
        {
            for(int index = 0; index < dropItem.size(); index++)
            {
                ItemStack dropItemStack = dropItem.get(index).getItemStack();
                if (mineral.containsKey(dropItemStack.getType()))
                {
                    String prevRate = "e";
                    CustomStack stack = CustomStack.getInstance(mineral.get(dropItemStack.getType()) + prevRate);
                    ItemStack itemStack = stack.getItemStack();
                    for (int i = 0; i < dropItem.get(index).getItemStack().getAmount(); i++)
                    {
                        String rate = RandomMineral();
                        if(rate.equals("s"))
                        {
                            stack = CustomStack.getInstance(mineral.get(dropItemStack.getType()) + rate);
                            itemStack = stack.getItemStack();
                            prevRate = rate;
                            break;
                        }
                        else if(prevRate.compareTo(rate) > 0)
                        {
                            stack = CustomStack.getInstance(mineral.get(dropItemStack.getType()) + rate);
                            itemStack = stack.getItemStack();
                            prevRate = rate;
                        }
                    }
                    dropItem.get(index).setItemStack(itemStack);
                }
            }
        }
        else
        {
            breakfurnace.remove(player);
        }
    }

    // 플레이어 한테 아이템 주는 함수
}
