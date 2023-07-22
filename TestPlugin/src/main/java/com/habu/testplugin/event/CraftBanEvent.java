package com.habu.testplugin.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftBanEvent implements Listener
{
    List<Material> banBLockList = new ArrayList<Material>(Arrays.asList(
            Material.BEACON,
            Material.TNT
    ));
    @EventHandler
    public void CraftBan(CraftItemEvent event)
    {
        ItemStack craftItem = event.getCurrentItem();
        if(banBLockList.contains(craftItem.getType()))
        {
            event.setCancelled(true);
        }
    }
}