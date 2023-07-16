package com.habu.testplugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class PlayerTeleport implements Listener
{
    static HashMap<UUID, Integer> countMap = new HashMap<UUID, Integer>();
    public static void teleportPlayer(Player player, Location loc, ItemStack itemStack)
    {
        UUID uuid = player.getUniqueId();
        if(countMap.containsKey(uuid))
        {
            return;
        }
        else
        {
            countMap.put(uuid, 5);
        }
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(countMap.get(uuid) != -1)
                {
                    if(countMap.get(uuid) != 0)
                    {
                        player.sendMessage( ChatColor.BLUE + "" + countMap.get(uuid) + "초 후 광장으로 귀환합니다.");
                        countMap.put(uuid, countMap.get(uuid)-1);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.BLUE + "광장으로 귀환합니다..!");
                        player.teleport(loc);
                        countMap.remove(uuid);
                        itemStack.setAmount(itemStack.getAmount()-1);
                        this.cancel();
                    }
                }
                else
                {
                player.sendMessage(ChatColor.RED + "귀환이 취소되었습니다!");
                    countMap.remove(uuid);
                    this.cancel();
                }
            }
        }.runTaskTimer(TestPlugin.getPlugin(), 0, 20L);
    }

    @EventHandler
    public void movePlayer(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        Location locFrom = event.getFrom();
        Location locTo = event.getTo();

        if(locFrom.getX() != locTo.getX() || locFrom.getY() != locTo.getY() || locFrom.getZ() != locTo.getZ())
        {
            if(countMap.containsKey(uuid))
                countMap.put(uuid, -1);
        }
    }

    @EventHandler
    public void damagedPlayer(EntityDamageEvent event)
    {
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            UUID uuid = player.getUniqueId();

            if(countMap.containsKey(uuid))
                countMap.put(uuid, -1);
        }
    }
}
