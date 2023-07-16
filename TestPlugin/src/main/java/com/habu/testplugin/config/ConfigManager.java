package com.habu.testplugin.config;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.shop.WoodCutterShop;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;

public class ConfigManager
{
    private final TestPlugin plugin = TestPlugin.getPlugin();
    private final String path = plugin.getDataFolder().getAbsolutePath();

    private HashMap<String, ConfigMaker> configSet = new HashMap<>();

    
    // config 전체 재시작
    public ConfigManager()
    {
        configSet.put("whitelist", new ConfigMaker(path, "whitelist.yml"));
        configSet.put("message", new ConfigMaker(path, "message.yml"));
        configSet.put("player", new ConfigMaker(path, "player.yml"));
        configSet.put("generalshop", new ConfigMaker(path, "generalshop.yml"));
        configSet.put("fishershop", new ConfigMaker(path, "fishershop.yml"));
        configSet.put("minershop", new ConfigMaker(path, "minershop.yml"));
        configSet.put("farmershop", new ConfigMaker(path, "farmershop.yml"));
        configSet.put("woodcuttershop", new ConfigMaker(path, "woodcuttershop.yml"));
        loadSettings();
        saveConfigs();
    }

    
    // config 전체 리로드
    public void reloadConfigs()
    {
        for(String key : configSet.keySet())
        {
            plugin.getLogger().info(key);
            configSet.get(key).reloadConfig();
        }
    }

    
    // 특정 config 리로드
    public void reloadConfig(String fileName)
    {
        configSet.get(fileName).reloadConfig();
    }

    
    // config 전체 저장
    public void saveConfigs()
    {
        for(String key : configSet.keySet())
        {
            configSet.get(key).saveConfig();
        }
    }
    
    
    // 특정 config 저장
    public void saveConfig(String fileName)
    {
        configSet.get(fileName).saveConfig();
    }

    
    // config 가져오기
    public FileConfiguration getConfig(String fileName)
    {
        return configSet.get(fileName).getConfig();
    }

    // config 컬러챗 가져오기??
    public String getConfigColorString(String fileName, String path)
    {
        return ChatColor.translateAlternateColorCodes('&', getConfig(fileName).getString(path));
    }

    
    // config 기본 세팅
    public void loadSettings()
    {
        FileConfiguration whitelistConfig = getConfig("whitelist");
        FileConfiguration messageConfig = getConfig("message");
        FileConfiguration generalshopConfig = getConfig("generalshop");
        FileConfiguration fishershopConfig = getConfig("fishershop");
        FileConfiguration minershopConfig = getConfig("minershop");
        FileConfiguration farmershopConfig = getConfig("farmershop");
        FileConfiguration woodcuttershopConfig = getConfig("woodcuttershop");

        whitelistConfig.options().copyDefaults(true);
        getConfig("player").options().copyDefaults(true);
        messageConfig.options().copyDefaults(true);
        generalshopConfig.options().copyDefaults(true);
        fishershopConfig.options().copyDefaults(true);
        minershopConfig.options().copyDefaults(true);
        farmershopConfig.options().copyDefaults(true);
        woodcuttershopConfig.options().copyDefaults(true);

        messageConfig.addDefault("join-message.display-message", true);
        messageConfig.addDefault("join-message.message", "&7{playername} 님이 접속하였습니다.");
        messageConfig.addDefault("quit-message.display-message", true);
        messageConfig.addDefault("quit-message.message", "&7{playername} 님이 접속을 종료하셨습니다.");
        messageConfig.addDefault("kick-message.display-message", true);
        messageConfig.addDefault("kick-message.message", "&7{playername} 님이 {reason} 이유로 인해 퇴장당하셨습니다.");
        messageConfig.addDefault("new-player-join-message.display-message", true);
        messageConfig.addDefault("new-player-join-message.message", "&7{playername} 이(가) 걸음마를 시작했습니다.");

        // 잡화상점
        generalshopConfig.addDefault("area_protect.price", 50000);
        generalshopConfig.addDefault("door_lock.price", 5000);
        generalshopConfig.addDefault("inventory_save.price", 2500);
        generalshopConfig.addDefault("whitelist_adder.price", 25000);
        generalshopConfig.addDefault("job_initialization.price", 10000);
        generalshopConfig.addDefault("return_village.price", 2500);

        // 낚시꾼
        fishershopConfig.addDefault("fisher_selecter.price", 1000);
        fishershopConfig.addDefault("cod.price", 20);
        fishershopConfig.addDefault("salmon.price", 38);
        fishershopConfig.addDefault("pufferfish.price", 44);
        fishershopConfig.addDefault("tropical_fish.price", 49);

        fishershopConfig.addDefault("fisher_selecter.volume", 0);
        fishershopConfig.addDefault("cod.volume", 0);
        fishershopConfig.addDefault("salmon.volume", 0);
        fishershopConfig.addDefault("pufferfish.volume", 0);
        fishershopConfig.addDefault("tropical_fish.volume", 0);

        // 광부
        minershopConfig.addDefault("miner_selecter.price", 1000);
        minershopConfig.addDefault("coal.price", 1);
        minershopConfig.addDefault("copper.price", 3);
        minershopConfig.addDefault("iron.price", 4);
        minershopConfig.addDefault("gold.price", 10);
        minershopConfig.addDefault("lapis_lazuli.price", 6);
        minershopConfig.addDefault("redstone.price", 3);
        minershopConfig.addDefault("diamond.price", 80);
        minershopConfig.addDefault("emerald.price", 90);
        minershopConfig.addDefault("amethyst_shard.price", 120);
        minershopConfig.addDefault("quartz.price", 12);
        minershopConfig.addDefault("netherite.price", 4000);

        minershopConfig.addDefault("miner_selecter.volume", 0);
        minershopConfig.addDefault("coal.volume", 0);
        minershopConfig.addDefault("copper.volume", 0);
        minershopConfig.addDefault("iron.volume", 0);
        minershopConfig.addDefault("gold.volume", 0);
        minershopConfig.addDefault("lapis_lazuli.volume", 0);
        minershopConfig.addDefault("redstone.volume", 0);
        minershopConfig.addDefault("diamond.volume", 0);
        minershopConfig.addDefault("emerald.volume", 0);
        minershopConfig.addDefault("amethyst_shard.volume", 0);
        minershopConfig.addDefault("quartz.volume", 0);
        minershopConfig.addDefault("netherite.volume", 0);

        // 농부
        farmershopConfig.addDefault("farmer_selecter.price", 1000);
        farmershopConfig.addDefault("wheat.price", 7);
        farmershopConfig.addDefault("carrot.price", 4);
        farmershopConfig.addDefault("potato.price", 4);
        farmershopConfig.addDefault("beetroot.price", 10);
        farmershopConfig.addDefault("melon.price", 16);
        farmershopConfig.addDefault("pumpkin.price", 60);
        farmershopConfig.addDefault("sweet_berries.price", 4);
        farmershopConfig.addDefault("cocoa_beans.price", 13);
        farmershopConfig.addDefault("nether_wart.price", 50);

        farmershopConfig.addDefault("farmer_selecter.volume", 0);
        farmershopConfig.addDefault("wheat.volume", 0);
        farmershopConfig.addDefault("carrot.volume", 0);
        farmershopConfig.addDefault("potato.volume", 0);
        farmershopConfig.addDefault("beetroot.volume", 0);
        farmershopConfig.addDefault("melon.volume", 0);
        farmershopConfig.addDefault("pumpkin.volume", 0);
        farmershopConfig.addDefault("sweet_berries.volume", 0);
        farmershopConfig.addDefault("cocoa_beans.volume", 0);
        farmershopConfig.addDefault("nether_wart.volume", 0);

        // 나무꾼
        woodcuttershopConfig.addDefault("woodcutter_selecter.price", 1000);
        woodcuttershopConfig.addDefault("oak.price", 4);
        woodcuttershopConfig.addDefault("spruce.price", 4);
        woodcuttershopConfig.addDefault("birch.price", 4);
        woodcuttershopConfig.addDefault("jungle.price", 4);
        woodcuttershopConfig.addDefault("acacia.price", 4);
        woodcuttershopConfig.addDefault("dark_oak.price", 4);
        woodcuttershopConfig.addDefault("mangrove.price", 4);
        woodcuttershopConfig.addDefault("crimson_stem.price", 6);
        woodcuttershopConfig.addDefault("mushroom_stem.price", 5);
        woodcuttershopConfig.addDefault("warped_stem.price", 7);
        woodcuttershopConfig.addDefault("apple.price", 12);
        woodcuttershopConfig.addDefault("golden_apple.price", 70);
        woodcuttershopConfig.addDefault("enchanted_golden_apple.price", 200);

        farmershopConfig.addDefault("woodcutter_selecter.volume", 0);
        woodcuttershopConfig.addDefault("oak.volume", 0);
        woodcuttershopConfig.addDefault("spruce.volume", 0);
        woodcuttershopConfig.addDefault("birch.volume", 0);
        woodcuttershopConfig.addDefault("jungle.volume", 0);
        woodcuttershopConfig.addDefault("acacia.volume", 0);
        woodcuttershopConfig.addDefault("dark_oak.volume", 0);
        woodcuttershopConfig.addDefault("mangrove.volume", 0);
        woodcuttershopConfig.addDefault("crimson_stem.volume", 0);
        woodcuttershopConfig.addDefault("mushroom_stem.volume", 0);
        woodcuttershopConfig.addDefault("warped_stem.volume", 0);
        woodcuttershopConfig.addDefault("apple.volume", 0);
        woodcuttershopConfig.addDefault("golden_apple.volume", 0);
        woodcuttershopConfig.addDefault("enchanted_golden_apple.volume", 0);
    }
}
