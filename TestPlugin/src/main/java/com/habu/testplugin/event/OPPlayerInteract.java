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
    private static int jobshop = 0;

    public static boolean getSwitch()
    {
        return on;
    }

    public static void Switch(boolean bool)
    {
        on = bool;
    }

    public static void setJobNum(int num)
    {
        jobshop = num;
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

        switch (jobshop)
        {
            case 1:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.AQUA + "바다 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case 2:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.YELLOW + "광물 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case 3:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.GREEN + "작물 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case 4:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.GOLD + "나무 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case 5:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.WHITE + "잡화 상점");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case 11:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.AQUA + "[낚시꾼]" + ChatColor.WHITE + " 인생");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case 22:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.YELLOW + "[광부]" + ChatColor.WHITE + " 인생");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case 33:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.GREEN + "[농부]" + ChatColor.WHITE + " 인생");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            case 44:
                npc.setSilent(true);
                npc.setCustomName(ChatColor.GOLD + "[나무꾼]" + ChatColor.WHITE + " 인생");
                npc.setCustomNameVisible(true);
                npc.setInvulnerable(true);
                break;
            default:
        }
    }
}
