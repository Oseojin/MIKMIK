package com.habu.testplugin.event.job;

import com.habu.testplugin.event.ChatEvent;
import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.PlayerManager;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.*;
import java.util.*;

public class Fisher implements Listener
{
    Random random = new Random();

    String pouchId = "customitems:fishpouch_";

    List<Material> treasureList = new ArrayList<>(Arrays.asList(
            Material.BOW, Material.ENCHANTED_BOOK, Material.FISHING_ROD, Material.NAME_TAG, Material.NAUTILUS_SHELL, Material.SADDLE, Material.LILY_PAD
    ));

    List<Material> trashList = new ArrayList<>(Arrays.asList(
            Material.BAMBOO, Material.BOWL, Material.FISHING_ROD, Material.LEATHER, Material.LEATHER_BOOTS, Material.ROTTEN_FLESH, Material.STICK, Material.STRING, Material.POTION, Material.BONE, Material.INK_SAC, Material.TRIPWIRE_HOOK
    ));



    HashMap<Enchantment, Integer> S_Rate = new HashMap<Enchantment, Integer>()
    {{
        put(Enchantment.MENDING, 1);
        put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        put(Enchantment.DAMAGE_ALL, 5);
        put(Enchantment.ARROW_INFINITE, 1);
        put(Enchantment.DIG_SPEED, 5);
        put(Enchantment.LURE, 3);
        put(Enchantment.LUCK, 3);
        put(Enchantment.LOOT_BONUS_BLOCKS, 3);
    }};

    HashMap<Enchantment, Integer> A_Rate = new HashMap<Enchantment, Integer>()
    {{
        put(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        put(Enchantment.DAMAGE_ALL, 4);
        put(Enchantment.DIG_SPEED, 4);
        put(Enchantment.LURE, 2);
        put(Enchantment.LUCK, 2);
        put(Enchantment.LOOT_BONUS_BLOCKS, 2);
        put(Enchantment.PIERCING, 5);
        put(Enchantment.DAMAGE_ARTHROPODS, 5);
        put(Enchantment.PROTECTION_FALL, 4);
        put(Enchantment.LOOT_BONUS_MOBS, 3);
        put(Enchantment.ARROW_DAMAGE, 5);
        put(Enchantment.DURABILITY, 3);
        put(Enchantment.LOYALTY, 3);
        put(Enchantment.DAMAGE_UNDEAD, 5);
        put(Enchantment.CHANNELING, 1);
    }};

    HashMap<Enchantment, Integer> B_Rate = new HashMap<Enchantment, Integer>()
    {{
        put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        put(Enchantment.DAMAGE_ALL, 3);
        put(Enchantment.DIG_SPEED, 3);
        put(Enchantment.LURE, 1);
        put(Enchantment.LUCK, 1);
        put(Enchantment.LOOT_BONUS_BLOCKS, 1);
        put(Enchantment.IMPALING, 4);
        put(Enchantment.DAMAGE_ARTHROPODS, 4);
        put(Enchantment.PROTECTION_FALL, 3);
        put(Enchantment.LOOT_BONUS_MOBS, 2);
        put(Enchantment.ARROW_DAMAGE, 4);
        put(Enchantment.DURABILITY, 2);
        put(Enchantment.LOYALTY, 2);
        put(Enchantment.DAMAGE_UNDEAD, 4);
        put(Enchantment.PROTECTION_FIRE, 4);
        put(Enchantment.PROTECTION_EXPLOSIONS, 4);
        put(Enchantment.PROTECTION_PROJECTILE, 4);
        put(Enchantment.KNOCKBACK, 2);
        put(Enchantment.ARROW_FIRE, 1);
        put(Enchantment.SWIFT_SNEAK, 3);
        put(Enchantment.MULTISHOT, 1);
        put(Enchantment.THORNS, 3);
        put(Enchantment.RIPTIDE, 3);
    }};

    HashMap<Enchantment, Integer> C_Rate = new HashMap<Enchantment, Integer>()
    {{
        put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        put(Enchantment.DAMAGE_ALL, 2);
        put(Enchantment.DIG_SPEED, 2);
        put(Enchantment.PIERCING, 3);
        put(Enchantment.DAMAGE_ARTHROPODS, 3);
        put(Enchantment.PROTECTION_FALL, 2);
        put(Enchantment.LOOT_BONUS_MOBS, 1);
        put(Enchantment.ARROW_DAMAGE, 3);
        put(Enchantment.DURABILITY, 1);
        put(Enchantment.LOYALTY, 1);
        put(Enchantment.DAMAGE_UNDEAD, 3);
        put(Enchantment.PROTECTION_FIRE, 3);
        put(Enchantment.PROTECTION_EXPLOSIONS, 3);
        put(Enchantment.PROTECTION_PROJECTILE, 3);
        put(Enchantment.KNOCKBACK, 1);
        put(Enchantment.SWIFT_SNEAK, 2);
        put(Enchantment.THORNS, 2);
        put(Enchantment.RIPTIDE, 2);
        put(Enchantment.SOUL_SPEED, 3);
        put(Enchantment.WATER_WORKER, 1);
        put(Enchantment.FROST_WALKER, 2);
        put(Enchantment.PIERCING, 4);
        put(Enchantment.OXYGEN, 3);
        put(Enchantment.ARROW_KNOCKBACK, 2);
        put(Enchantment.SWEEPING_EDGE, 3);
        put(Enchantment.DEPTH_STRIDER, 3);
        put(Enchantment.QUICK_CHARGE, 3);
    }};

    HashMap<Enchantment, Integer> D_Rate = new HashMap<Enchantment, Integer>()
    {{
        put(Enchantment.DAMAGE_ALL, 1);
        put(Enchantment.DIG_SPEED, 1);
        put(Enchantment.IMPALING, 2);
        put(Enchantment.DAMAGE_ARTHROPODS, 2);
        put(Enchantment.PROTECTION_FALL, 1);
        put(Enchantment.ARROW_DAMAGE, 2);
        put(Enchantment.DAMAGE_UNDEAD, 2);
        put(Enchantment.PROTECTION_FIRE, 2);
        put(Enchantment.PROTECTION_EXPLOSIONS, 2);
        put(Enchantment.PROTECTION_PROJECTILE, 2);
        put(Enchantment.SWIFT_SNEAK, 1);
        put(Enchantment.THORNS, 1);
        put(Enchantment.RIPTIDE, 1);
        put(Enchantment.SOUL_SPEED, 2);
        put(Enchantment.FROST_WALKER, 1);
        put(Enchantment.PIERCING, 3);
        put(Enchantment.OXYGEN, 2);
        put(Enchantment.ARROW_KNOCKBACK, 1);
        put(Enchantment.SWEEPING_EDGE, 2);
        put(Enchantment.DEPTH_STRIDER, 2);
        put(Enchantment.QUICK_CHARGE, 2);
        put(Enchantment.SILK_TOUCH, 1);
        put(Enchantment.FIRE_ASPECT, 2);
    }};


    HashMap<Enchantment, Integer> E_Rate = new HashMap<Enchantment, Integer>()
    {{
        put(Enchantment.IMPALING, 1);
        put(Enchantment.DAMAGE_ARTHROPODS, 1);
        put(Enchantment.ARROW_DAMAGE, 1);
        put(Enchantment.DAMAGE_UNDEAD, 1);
        put(Enchantment.PROTECTION_FIRE, 1);
        put(Enchantment.PROTECTION_EXPLOSIONS, 1);
        put(Enchantment.PROTECTION_PROJECTILE, 1);
        put(Enchantment.SOUL_SPEED, 1);
        put(Enchantment.PIERCING, 1);
        put(Enchantment.PIERCING, 2);
        put(Enchantment.OXYGEN, 1);
        put(Enchantment.SWEEPING_EDGE, 1);
        put(Enchantment.DEPTH_STRIDER, 1);
        put(Enchantment.QUICK_CHARGE, 1);
        put(Enchantment.FIRE_ASPECT, 1);
        put(Enchantment.VANISHING_CURSE, 1);
        put(Enchantment.BINDING_CURSE, 1);
    }};

    HashMap<String, HashMap<Enchantment, Integer>> pouchList = new HashMap<String, HashMap<Enchantment, Integer>>()
    {{
        put("customitems:fishpouch_e", E_Rate);
        put("customitems:fishpouch_d", D_Rate);
        put("customitems:fishpouch_c", C_Rate);
        put("customitems:fishpouch_b", B_Rate);
        put("customitems:fishpouch_a", A_Rate);
        put("customitems:fishpouch_s", S_Rate);
    }};

    final double MAXIMUM_SIZE = 100;
    private String RandomPouch()
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

    private String RandomSize(double doubleSize)
    {
        // Huge >>> Large > Medium > Small // 등급
        // 거대  >>> 큰    > 보통    > 작은  // 등급(한국어)
        // *4   >>> *2    > *1     > *1/2 // 가격
        // 5    >>> 20    > 60     > 15   // 단일 확률
        // 95   >>> 75    > 15     > 0    // 실 구현
        String size = "";

        if(doubleSize >= 120)
        {
            size = ChatColor.RED + "거대한";
        }
        else if (doubleSize >= 75)
        {
            size = ChatColor.GOLD + "큰";
        }
        else if (doubleSize >= 30)
        {
            size = ChatColor.AQUA + "보통";
        }
        else
        {
            size = ChatColor.GREEN + "작은";
        }

        return size;
    }

    private ItemStack RandomPouchItem(HashMap<Enchantment, Integer> enchantmentMap, Player player)
    {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
        Object[] keys = enchantmentMap.keySet().toArray();
        Enchantment randEnchantment = (Enchantment) keys[random.nextInt(keys.length)];
        bookMeta.addStoredEnchant(randEnchantment, enchantmentMap.get(randEnchantment), true);
        book.setItemMeta(bookMeta);

        return book;
    }

    @EventHandler
    public void FisherOpenPouch(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
        UUID uuid = player.getUniqueId();
        ItemStack itemStack = player.getItemInHand();

        String playerJob = PlayerManager.GetJob(player);
        if(playerJob.equals(JobNameManager.FisherName) && (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)))
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
                if(pouchList.containsKey(itemId))
                {
                    // 랜덤 아이템
                    ItemStack randItem = RandomPouchItem(pouchList.get(itemId), player);
                    player.getInventory().addItem(randItem);

                    if(itemStack.getAmount() >= 1)
                    {
                        int amount = itemStack.getAmount() - 1;
                        itemStack.setAmount(amount);
                    }
                    else
                    {
                        player.getInventory().removeItem(itemStack);
                    }
                }
            }
        }
    }

    @EventHandler
    public void FisherOnFishing(PlayerFishEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        FishHook hook = event.getHook();
        String playerJob = PlayerManager.GetJob(player);
        if(playerJob.equals(JobNameManager.FisherName))
            hook.setWaitTime(60, 300);

        if(event.getState() == PlayerFishEvent.State.REEL_IN) // 낚시 찌를 너무 빨리 당긴 경우
        {

        }
        else if(event.getState() == PlayerFishEvent.State.FAILED_ATTEMPT) // 낚시 찌를 너무 늦게 당긴 경우
        {

        }
        else if(event.getState() == PlayerFishEvent.State.BITE) // 물고기가 낚시 찌를 문 경우
        {

        }
        else if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH) // 물고기를 잡은 경우
        {
            if(playerJob.equals(JobNameManager.FisherName))
            {
                Item dropItem = (Item) event.getCaught();
                ItemStack dropItemStack = dropItem.getItemStack();
                if(treasureList.contains(dropItemStack.getType()))
                {
                    String rate = RandomPouch();
                    player.sendMessage("[" + rate.toUpperCase() + "]" + "등급 주머니를 낚았습니다!");
                    CustomStack stack = CustomStack.getInstance(pouchId+rate);
                    ItemStack itemStack = stack.getItemStack();
                    dropItem.setItemStack(itemStack);
                }
                else if(trashList.contains(dropItemStack.getType()))
                {
                    String rate = RandomPouch();
                    player.sendMessage("[" + rate.toUpperCase() + "]" + "등급 주머니를 낚았습니다!");
                    CustomStack stack = CustomStack.getInstance(pouchId+rate);
                    ItemStack itemStack = stack.getItemStack();
                    dropItem.setItemStack(itemStack);
                }
                else
                {
                    ItemMeta dropItemMeta = dropItemStack.getItemMeta();
                    if(dropItemStack.getType().equals(Material.COD))
                    {
                        dropItemMeta.setDisplayName("대구");
                    }
                    else if (dropItemStack.getType().equals(Material.SALMON))
                    {
                        dropItemMeta.setDisplayName("연어");
                    }
                    else if (dropItemStack.getType().equals(Material.TROPICAL_FISH))
                    {
                        dropItemMeta.setDisplayName("열대어");
                    }else if (dropItemStack.getType().equals(Material.PUFFERFISH))
                    {
                        dropItemMeta.setDisplayName("복어");
                    }
                    dropItemStack.setItemMeta(dropItemMeta);
                    double randomSize = Math.abs(Math.round((MAXIMUM_SIZE / 3 + (random.nextGaussian()) * 40) * 100) / 100.0);
                    String size = RandomSize(randomSize);
                    dropItemStack.setLore(Arrays.asList(size + " 크기", ChatColor.AQUA + "(" + randomSize + "cm)"));
                    player.sendMessage(size + " 크기의 " + dropItemMeta.getDisplayName() + " (" + randomSize + "cm)" + "를 잡았습니다!");
                    if(size.equals(ChatColor.RED + "거대한"))
                    {
                        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.WHITE + "님이 " + size + " 크기의 " + ChatColor.AQUA + dropItemMeta.getDisplayName() + ChatColor.WHITE + "을 잡았습니다!!");
                    }
                }
            }
        }
    }


    // 플레이어 한테 아이템 주는 함수
}
