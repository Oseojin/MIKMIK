package com.habu.testplugin.event;

import net.kyori.adventure.text.EntityNBTComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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
        Entity npc = event.getRightClicked();

        if(!on)
            return;

        switch (shopType)
        {
            case "바다상점":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.AQUA + "바다 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "광물상점":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.YELLOW + "광물 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "작물상점":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.GREEN + "작물 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "나무상점":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.GOLD + "나무 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "잡화상점":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.WHITE + "잡화 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "랜덤스폰알상점":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.LIGHT_PURPLE + "[랜덤스폰알]");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "낚시꾼전직":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.AQUA + "[낚시꾼]" + ChatColor.WHITE + " 인생");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "광부전직":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.YELLOW + "[광부]" + ChatColor.WHITE + " 인생");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "농부전직":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.GREEN + "[농부]" + ChatColor.WHITE + " 인생");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case "나무꾼전직":
                npc.setSilent(true);
                npc.setCustomName(ChatColor.GOLD + "[나무꾼]" + ChatColor.WHITE + " 인생");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            default:
        }
    }
}
