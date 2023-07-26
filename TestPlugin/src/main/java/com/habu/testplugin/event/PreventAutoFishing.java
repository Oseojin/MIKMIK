package com.habu.testplugin.event;

import com.habu.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PreventAutoFishing implements Listener
{
    HashMap<Player, Boolean> userClickedNoteBlock = new HashMap<Player, Boolean>();

    private void oneSecondLater(Player player)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                userClickedNoteBlock.remove(player);
            }
        }.runTaskLater(TestPlugin.getPlugin(), 100L);
    }

    @EventHandler
    public void InteractNoteBlock(PlayerInteractEvent event)
    {
        Thread NoteBlockSecond = new Thread(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        oneSecondLater(event.getPlayer());
                    }
                }
        );

        if(event.getClickedBlock() == null)
            return;
        if(event.getClickedBlock().getType().equals(Material.NOTE_BLOCK))
        {
            if(!userClickedNoteBlock.containsKey(event.getPlayer()))
            {
                userClickedNoteBlock.put(event.getPlayer(), true);
                NoteBlockSecond.run();
            }
        }
    }

    @EventHandler
    public void Fishing(PlayerFishEvent event)
    {
        if(event.getState().equals(PlayerFishEvent.State.BITE))
        {
            if(userClickedNoteBlock.containsKey(event.getPlayer()) && userClickedNoteBlock.get(event.getPlayer()))
            {
                event.setCancelled(true);
            }
        }
    }
}
