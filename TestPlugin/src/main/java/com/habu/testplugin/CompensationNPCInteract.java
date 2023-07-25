package com.habu.testplugin;

import com.habu.testplugin.db.player_db_connect;
import com.habu.testplugin.manager.JobNameManager;
import com.habu.testplugin.manager.NPCNameManager;
import com.habu.testplugin.manager.TitleNameManager;
import com.habu.testplugin.shop.jobshop.FisherShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CompensationNPCInteract implements Listener
{
    FileConfiguration compensation = TestPlugin.getConfigManager().getConfig("compensation");
    private void PlayerSet(Player player, String jobName, Integer gold)
    {
        String title = "";

        switch (jobName)
        {
            case "운영자":
                title = TitleNameManager.Operator;
                break;
            case "무직":
                title = TitleNameManager.JobLess;
                break;
            case "낚시꾼":
                title = TitleNameManager.Fisher;
                break;
            case "광부":
                title = TitleNameManager.Miner;
                break;
            case "농부":
                title = TitleNameManager.Farmer;
                break;
            case "나무꾼":
                title = TitleNameManager.WoodCutter;
                break;
            case "사냥꾼":
                title = TitleNameManager.Hunter;
                break;
            default:
                title = "???";
        }

        switch (jobName)
        {
            case "운영자":
                jobName = JobNameManager.OperatorName;
                break;
            case "무직":
                jobName = JobNameManager.JobLessName;
                break;
            case "낚시꾼":
                jobName = JobNameManager.FisherName;
                break;
            case "광부":
                jobName = JobNameManager.MinerName;
                break;
            case "농부":
                jobName = JobNameManager.FarmerName;
                break;
            case "나무꾼":
                jobName = JobNameManager.WoodCutterName;
                break;
            case "사냥꾼":
                jobName = JobNameManager.HunterName;
                break;
            default:
                jobName = "???";
        }

        TestPlugin.db_conn.SetJob(player, jobName);
        TestPlugin.db_conn.SetTitle(player, title);
        TestPlugin.db_conn.AddGold(player, gold);
    }

    @EventHandler
    public void PlayerClickShopNPC(PlayerInteractEntityEvent event)
    {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        String npcName = entity.getName();
        String playerJob = TestPlugin.db_conn.GetJob(player);
        if (npcName.equals(NPCNameManager.CompensationNPCName))
        {
            if (compensation.contains(player.getName()))
            {
                if(compensation.getBoolean(player.getName() + ".isreceive"))
                {
                    player.sendMessage(ChatColor.RED + "이미 복구 받았습니다!");
                    return;
                }
                String jobName = compensation.getString(player.getName() + ".job");
                int gold = compensation.getInt(player.getName() + ".gold");
                PlayerSet(player, jobName, gold);
                compensation.set(player.getName() + ".isreceive", true);
                TestPlugin.getConfigManager().saveConfig("compensation");
            }
            else
            {
                player.sendMessage(ChatColor.AQUA + "아직 보상이 등록되지 않았습니다. 관리자를 호출하거나, 디스코드 서버에 복구 정보를 올렸는지 확인해 보세요.");
            }
        }
    }
}
