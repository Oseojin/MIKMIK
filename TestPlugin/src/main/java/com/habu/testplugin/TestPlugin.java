package com.habu.testplugin;

import com.habu.testplugin.command.*;
import com.habu.testplugin.command.operatorcommand.*;
import com.habu.testplugin.command.shop.*;
import com.habu.testplugin.config.ConfigManager;
import com.habu.testplugin.db.player_db_connect;
import com.habu.testplugin.domain.user;
import com.habu.testplugin.event.*;
import com.habu.testplugin.event.job.*;
import com.habu.testplugin.event.shop.*;
import com.habu.testplugin.event.shop.jobshop.FarmerShopClickEvent;
import com.habu.testplugin.event.shop.jobshop.FisherShopClickEvent;
import com.habu.testplugin.event.shop.jobshop.MinerShopClickEvent;
import com.habu.testplugin.event.shop.jobshop.WoodCutterShopClickEvent;
import com.habu.testplugin.event.shop.randomshop.RandomSpawnEggShopClickEvent;
import com.habu.testplugin.event.shop.selectshop.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class TestPlugin extends JavaPlugin
{
    public static player_db_connect db_conn = player_db_connect.getInstance();
    public static HashMap<String, user> User_List = new HashMap<String, user>();
    private static ConfigManager configManager;

    private void REGISTEREVENT()
    {
        // 기본
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerUseItemEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        getServer().getPluginManager().registerEvents(new CraftBanEvent(), this);

        // 직업
        getServer().getPluginManager().registerEvents(new UseJobSelecter(), this);

        getServer().getPluginManager().registerEvents(new FisherSelectShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new FisherShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new Fisher(), this);

        getServer().getPluginManager().registerEvents(new MinerSelectShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new MinerShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new Miner(), this);


        getServer().getPluginManager().registerEvents(new FarmerSelectShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new FarmerShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new Farmer(), this);

        getServer().getPluginManager().registerEvents(new WoodCutterSelectShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new WoodCutterShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new WoodCutter(), this);

        getServer().getPluginManager().registerEvents(new HunterSelectShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new Hunter(), this);;

        // 경제
        getServer().getPluginManager().registerEvents(new UsingCheckEvent(), this);

        getServer().getPluginManager().registerEvents(new PlayerInteractNPC(), this);

        getServer().getPluginManager().registerEvents(new FisherShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new MinerShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new FarmerShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new WoodCutterShopClickEvent(), this);

        getServer().getPluginManager().registerEvents(new GeneralShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new RandomSpawnEggShopClickEvent(), this);

        // 운영자
        getServer().getPluginManager().registerEvents(new BlockTestEvent(), this);
        getServer().getPluginManager().registerEvents(new OPPlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new CompensationNPCInteract(), this);
    }

    private void SETCOMMAND()
    {
        // 기본
        getCommand("calloperator").setExecutor(new CallOperator());

        // 직업

        // 경제
        getCommand("check").setExecutor(new IssuingCheckCommand());

        // 운영자
        getCommand("statcheck").setExecutor(new StatChecker());
        getCommand("switchcheckblock").setExecutor(new BlockCheckCommand());
        getCommand("shopnpcapply").setExecutor(new NPCSHOPAPPLY());
        getCommand("goldsetplayer").setExecutor(new SetPlayerGold());
        getCommand("goldaddplayer").setExecutor(new AddPlayerGold());
        getCommand("jobsetplayer").setExecutor(new SetPlayerJob());
        getCommand("titlesetplayer").setExecutor(new SetPlayerTitle());
        getCommand("shutdown").setExecutor(new SHUTDOWN());
        getCommand("dbload").setExecutor(new DBLOADPLAYER());

        getCommand("compensationset").setExecutor(new CompensationConfigSet());
    }

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        REGISTEREVENT();
        SETCOMMAND();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }

    public static TestPlugin getPlugin()
    {
        return JavaPlugin.getPlugin(TestPlugin.class);
    }

    public static ConfigManager getConfigManager()
    {
        if(configManager == null)
        {
            configManager = new ConfigManager();
        }
        return configManager;
    }
}
