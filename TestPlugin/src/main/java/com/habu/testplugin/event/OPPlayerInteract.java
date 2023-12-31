package com.habu.testplugin.event;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.NPCNameManager;
import net.kyori.adventure.text.EntityNBTComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OPPlayerInteract implements Listener
{
    private static boolean on = false;
    private static String shopType = "";

    public static boolean getSwitch()
    {
        return on;
    }

    public static void Switch(boolean bool)
    {
        on = bool;
    }

    private Boolean isOpen = false;

    public static void setShopType(String name)
    {
        shopType = name;
    }


    @EventHandler
    public void InteractNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        if(!player.isOp())
            return;

        if(isOpen)
        {
            return;
        }
        isOpen = true;

        Entity npc = event.getRightClicked();

        if(!on)
            return;

        switch (shopType)
        {
            case "바다상점":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.FisherShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "광물상점":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.MinerShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "작물상점":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.FarmerShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "나무상점":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.WoodCutterShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "잡화상점":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.GeneralShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "랜덤스폰알상점":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.RandomSpawnEggShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "코인거래소":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.CoinShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "낚시꾼전직":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.FisherSelectShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "광부전직":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.MinerSelectShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "농부전직":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.FarmerSelectShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "나무꾼전직":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.WoodCutterSelectShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "사냥꾼전직":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.HunterSelectShopNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "보상":
                npc.setSilent(true);
                npc.setCustomName(NPCNameManager.CompensationNPCName);
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            default:
        }

        new BukkitRunnable()
        {

            @Override
            public void run()
            {
                isOpen = false;
            }
        }.runTaskLater(TestPlugin.getPlugin(), 20L);
    }
}
