package com.habu.testplugin.Hidden;

import com.habu.testplugin.Hidden.LightningCharger;
import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class LightningChargerManager
{
    public static FileConfiguration HiddenConfig = TestPlugin.getConfigManager().getConfig("hidden");
    public static String LightningChargerName = "LightningCharger";
    private static boolean isOpen = false;

    public static void FINDHIDDEN(Player player)
    {
        String playerName = player.getName();
        if(isOpen)
        {
            return;
        }
        isOpen = true;
        if (!HiddenConfig.getBoolean(LightningChargerName + ".find"))
        {
            PlayerManager.SetHidden(player, LightningChargerName, 1);
            player.sendTitle(ChatColor.YELLOW + "너는 벼락에게 선택받았다", ChatColor.YELLOW + "히든: 뇌창" + ChatColor.GOLD + " 의 자격을 획득했습니다.");
            Bukkit.broadcastMessage(ChatColor.AQUA + playerName + ChatColor.YELLOW + "이 ???의 자격을 획득했습니다.");
            HiddenConfig.set(LightningChargerName + ".find", true);
            TestPlugin.getConfigManager().saveConfig("hidden");
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    player.sendTitle(ChatColor.YELLOW + "벼락을 다루기 위해서는 벼락을 느껴야 한다", ChatColor.WHITE + "전직 퀘스트: 벼락을 맞으세요");
                    isOpen = false;
                }
            }.runTaskLater(TestPlugin.getPlugin(), 100L);
        }
        else if (PlayerManager.GetHidden(player, LightningChargerName) > 0)
        {
            if(HiddenConfig.getBoolean(LightningChargerName + ".complete"))
            {
                player.sendTitle(ChatColor.YELLOW + "무슨일이냐", ChatColor.WHITE + "이미 완료한 퀘스트입니다.");
                isOpen = false;
            }
            else if(HiddenConfig.getBoolean(LightningChargerName + ".quest"))
            {
                if(player.getInventory().firstEmpty() == -1)
                {
                    player.sendTitle(ChatColor.YELLOW + "손에 든게 너무 많구나", ChatColor.WHITE + "인벤토리를 한 칸 이상 비워주세요");
                }
                else
                {
                    player.sendTitle(ChatColor.YELLOW + "이제 너 또한 벼락의 친우다", ChatColor.WHITE + "히든: 뇌창으로 전직 성공했습니다");
                    HiddenConfig.set(LightningChargerName + ".complete", true);
                    PlayerManager.SetTitle(player, "뇌창");
                    player.getInventory().addItem(LightningCharger.weapon);
                    TestPlugin.getConfigManager().saveConfig("hidden");
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
            else
            {
                player.sendTitle(ChatColor.YELLOW + "아직 벼락을 느끼지 못하는 구나", ChatColor.WHITE + "전직 퀘스트: 벼락을 맞으세요");
                isOpen = false;
            }
        }
        else
        {
            player.sendTitle(ChatColor.YELLOW + "너는 선택받은 인간이 아니다", "");
            player.teleport(player.getLocation().add(-10, 0 ,0));
            player.getWorld().spawnEntity(player.getLocation(), EntityType.LIGHTNING);
            isOpen = false;
        }
    }
}
