package com.habu.testplugin.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftBanEvent implements Listener
{
    @EventHandler
    public void CraftBan(CraftItemEvent event)
    {
        ItemStack craftItem = event.getCurrentItem();
        if(craftItem.getType().equals(Material.BEACON))
        {
            event.setCancelled(true);
        }
    }
}