package com.habu.testplugin.event;

import com.habu.testplugin.PlayerTeleport;
import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.config.ConfigManager;
import com.habu.testplugin.manager.ItemManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import javax.swing.*;

public class PlayerUseItemEvent implements Listener
{
    Location villageLocation = new Location(Bukkit.getWorlds().get(0), 273.5f, 67.0f, -315.5f);
    @EventHandler
    public void PlayerUseItem(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = player.getItemInHand();
        if(!action.equals(Action.RIGHT_CLICK_BLOCK) && !action.equals(Action.RIGHT_CLICK_AIR))
            return;
        if(itemStack.getType() == null || itemStack.getType().equals(ItemManager.gui_GrayGlassPane))
            return;
        if(itemStack.getType().equals(Material.ENCHANTED_BOOK) && itemStack.getLore().get(0).equals(ChatColor.WHITE + "모루를 통해 쿠폰의 이름을 초대하고자 하는 사람의 닉네임으로 바꾼 후 우클릭하면 초대할 수 있다."))
        {
            String inviteName = itemStack.getItemMeta().getDisplayName();
            if(inviteName.equals(ChatColor.AQUA + "서버 초대 쿠폰"))
            {
                player.sendMessage(ChatColor.DARK_AQUA + "사람 이름이 이거라구요..? 다시 생각해봐요..!");
                return;
            }
            player.sendMessage(ChatColor.GREEN + inviteName + "님이 서버에 초대되었습니다!");
            TestPlugin.getConfigManager().getConfig("whitelist").addDefault("players."+inviteName.toLowerCase(), true);
            TestPlugin.getConfigManager().saveConfig("whitelist");
            player.getInventory().removeItem(itemStack);
        }
        else if(itemStack.isSimilar(ItemManager.item_ReturnVillage))
        {
            PlayerTeleport.teleportPlayer(player, villageLocation, itemStack);
        }
    }
}