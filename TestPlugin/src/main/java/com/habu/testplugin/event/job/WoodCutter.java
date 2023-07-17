package com.habu.testplugin.event.job;

import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.PlayerManager;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class WoodCutter implements Listener
{
    Random random = new Random();

    List<Material> tree = new LinkedList<Material>(Arrays.asList(Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG
            , Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.MANGROVE_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM, Material.MUSHROOM_STEM));

    String bagId = "customitems:woodbag_";

    HashMap<String, Integer> bagList = new HashMap<String, Integer>()
    {{
       put("customitems:woodbag_e", 1);
       put("customitems:woodbag_d", 2);
       put("customitems:woodbag_c", 3);
       put("customitems:woodbag_b", 4);
       put("customitems:woodbag_a", 5);
       put("customitems:woodbag_s", 10);
    }};

    private String RandomBag()
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

    private void RandomApple(Player player ,int num)
    {
        ItemStack apple;

        for(int i = 0; i < num; i++)
        {
            // 인황 > 황금 > 사과 // 등급
            // 5   >  10 >  85  // 단일 확률
            // 95  >  85 >  0   // 실 확률

            int randItem = random.nextInt(100);

            if(randItem >= 95 - num) // 기본(E) 5%, 최대(S) 15%
            {
                apple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
            }
            else if (randItem >= 85 - num) // 기본(E) 10%, 최대(S) 20%
            {
                apple = new ItemStack(Material.GOLDEN_APPLE, 1);
            }
            else if (randItem >= -2 + (num * 2)) // 기본(E) 85%, 최대(S) 65%
            {
                apple = new ItemStack(Material.APPLE, 1);
            }
            else
            {
                apple = new ItemStack(Material.APPLE, 1);
            }

            player.getInventory().addItem(apple);
        }
    }

    @EventHandler
    public void WoodCutterOpenBag(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
        UUID uuid = player.getUniqueId();
        ItemStack itemStack = player.getItemInHand();

        String playerJob = PlayerManager.GetJob(player);
        if(playerJob.equals(JobNameManager.WoodCutterName) && (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)))
        {
            if(player.getInventory().firstEmpty() == -1)
            {
                player.sendMessage("인벤토리 공간을 최소 1칸 비워주세요.");
                return;
            }
            CustomStack stack = CustomStack.byItemStack(itemStack);
            if(stack != null)
            {
                String itemId = stack.getNamespacedID();
                if(bagList.containsKey(itemId))
                {
                    // 랜덤 아이템
                    RandomApple(player, bagList.get(itemId));
                    int amount = itemStack.getAmount() - 1;
                    itemStack.setAmount(amount);
                }
            }
        }
    }

    @EventHandler
    public void BreakTree(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String playerJob = PlayerManager.GetJob(player);
        if(playerJob.equals(JobNameManager.WoodCutterName))
        {
            if(tree.contains(event.getBlock().getType()))
            {
                int randomNum = random.nextInt(10);
                if(randomNum >= 8) // 10% 확률로 나무가방 획득
                {
                    String rate = RandomBag();
                    player.sendMessage("나무에서 " + "[" + rate.toUpperCase() + "]" + "등급 가방이 떨어졌습니다!");
                    CustomStack stack = CustomStack.getInstance(bagId+rate);
                    ItemStack itemStack = stack.getItemStack();
                    player.getInventory().addItem(itemStack);
                }
            }
        }
    }

    // 플레이어 한테 아이템 주는 함수
}
