package com.habu.testplugin;

import com.habu.testplugin.Hidden.LightningNPC;
import com.habu.testplugin.command.*;
import com.habu.testplugin.command.operatorcommand.*;
import com.habu.testplugin.command.shop.*;
import com.habu.testplugin.config.ConfigManager;
import com.habu.testplugin.event.*;
import com.habu.testplugin.event.Battle.EnderDragonBattle;
import com.habu.testplugin.Hidden.LightningCharger;
import com.habu.testplugin.event.job.*;
import com.habu.testplugin.event.shop.*;
import com.habu.testplugin.event.shop.jobshop.FarmerShopClickEvent;
import com.habu.testplugin.event.shop.jobshop.FisherShopClickEvent;
import com.habu.testplugin.event.shop.jobshop.MinerShopClickEvent;
import com.habu.testplugin.event.shop.jobshop.WoodCutterShopClickEvent;
import com.habu.testplugin.event.shop.randomshop.RandomSpawnEggShopClickEvent;
import com.habu.testplugin.event.shop.selectshop.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin
{
    private static ConfigManager configManager;
    public static int count = 5;

    private void REGISTEREVENT()
    {
        // 기본
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
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

        // 히든
        getServer().getPluginManager().registerEvents(new LightningCharger(), this);
        getServer().getPluginManager().registerEvents(new LightningNPC(), this);


        // 경제
        getServer().getPluginManager().registerEvents(new UsingCheckEvent(), this);

        getServer().getPluginManager().registerEvents(new PlayerInteractNPC(), this);

        getServer().getPluginManager().registerEvents(new FisherShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new MinerShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new FarmerShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new WoodCutterShopClickEvent(), this);

        getServer().getPluginManager().registerEvents(new GeneralShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new RandomSpawnEggShopClickEvent(), this);
        getServer().getPluginManager().registerEvents(new CoinShopClickEvent(), this);

        // 전투
        getServer().getPluginManager().registerEvents(new EnderDragonBattle(), this);

        // 운영자
        getServer().getPluginManager().registerEvents(new BlockTestEvent(), this);
        getServer().getPluginManager().registerEvents(new OPPlayerInteract(), this);
    }

    private void SETCOMMAND()
    {
        // 기본
        getCommand("calloperator").setExecutor(new CallOperator());

        // 직업

        // 경제
        getCommand("check").setExecutor(new IssuingCheckCommand());
        getCommand("cointrade").setExecutor(new OpenCoinShop());

        // 운영자
        getCommand("statcheck").setExecutor(new StatChecker());
        getCommand("switchcheckblock").setExecutor(new BlockCheckCommand());
        getCommand("shopnpcapply").setExecutor(new NPCSHOPAPPLY());
        getCommand("goldsetplayer").setExecutor(new SetPlayerGold());
        getCommand("goldaddplayer").setExecutor(new AddPlayerGold());
        getCommand("jobsetplayer").setExecutor(new SetPlayerJob());
        getCommand("mycoin").setExecutor(new CheckCoin());
        getCommand("spawnweapon").setExecutor(new GetWeapon());
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
