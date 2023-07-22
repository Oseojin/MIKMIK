package com.habu.testplugin.shop;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.CoinManager;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CoinShop implements InventoryHolder
{

    static Inventory coinInv;
    static String configName = "coinshop";
    public static FileConfiguration shopConfig = TestPlugin.getConfigManager().getConfig(configName);
    static Random random = new Random();

    private static boolean isOpen = false;
    public static HashMap<String, Boolean> coinDelistings = new HashMap<String, Boolean>();

    private static int[][] invBasic =
            { {1,1,1,1,1,1,1,1,1}
            , {1,2,0,2,0,2,0,2,1}
            , {1,1,1,1,1,1,1,1,1} };

    private static Queue<ItemStack> sellItem = new LinkedList<ItemStack>();

    private static HashMap<Material, String> itemNamePare = new HashMap<Material, String>();

    public static boolean GetOpen()
    {
        return isOpen;
    }

    public static void initItemSetting()
    {
        itemNamePare.put(ItemManager.gui_PungsanDogCoin.getType(), "pungsandog_coin");
        itemNamePare.put(ItemManager.gui_MoleCoin.getType(), "mole_coin");
        itemNamePare.put(ItemManager.gui_BeetCoin.getType(), "beet_coin");
        itemNamePare.put(ItemManager.gui_KimchiCoin.getType(), "kimchi_coin");

        sellItem.add(ItemManager.gui_PungsanDogCoin);
        sellItem.add(ItemManager.gui_MoleCoin);
        sellItem.add(ItemManager.gui_BeetCoin);
        sellItem.add(ItemManager.gui_KimchiCoin);
    }

    public static void StartCoin()
    {
        new BukkitRunnable()
        {
            int currTime;
            @Override
            public void run()
            {
                isOpen = shopConfig.getBoolean("isopen.now");
                Thread coinReRoll = new Thread(
                        new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                CoinManager.getInstance().CoinReRoll();
                            }
                        }
                );
                if (isOpen)
                {
                    coinReRoll.run();
                    StopCoin();
                    this.cancel();
                }
                else
                {
                    currTime = shopConfig.getInt("starttimer.now");
                    if (currTime <= 0)
                    {
                        itemNamePare.forEach((key, value) ->
                        {
                            int basic_price = shopConfig.getInt(value + ".basic_price");
                            shopConfig.set(value + ".price", basic_price);
                            shopConfig.set(value + ".delisting", false);
                            TestPlugin.getConfigManager().saveConfig(configName);
                        });

                        Bukkit.broadcastMessage(ChatColor.AQUA + "오늘의 코인이 시작되었습니다.");
                        shopConfig.set("starttimer.now", shopConfig.get("starttimer.basic"));
                        shopConfig.set("isopen.now", true);
                        isOpen = shopConfig.getBoolean("isopen.now");
                        TestPlugin.getConfigManager().saveConfig(configName);
                        coinReRoll.run();
                        StopCoin();
                        this.cancel();
                    }
                    else
                    {
                        shopConfig.set("starttimer.now", --currTime);
                        TestPlugin.getConfigManager().saveConfig(configName);
                    }
                }
            }
        }.runTaskTimer(TestPlugin.getPlugin(), 0, 20L);
    }

    public static void StopCoin()
    {
        new BukkitRunnable()
        {
            int currTime;
            @Override
            public void run()
            {
                isOpen = shopConfig.getBoolean("isopen.now");
                if(isOpen)
                {
                    currTime = shopConfig.getInt("opentimer.now");
                    shopConfig.set("opentimer.now", --currTime);
                    if(currTime <= 0)
                    {
                        shopConfig.set("isopen.now", false);
                        isOpen = shopConfig.getBoolean("isopen.now");
                        shopConfig.set("opentimer.now", shopConfig.get("opentimer.basic"));
                        AllCoinSell();
                        this.cancel();
                    }
                    TestPlugin.getConfigManager().saveConfig(configName);
                }
            }
        }.runTaskTimer(TestPlugin.getPlugin(), 0, 20L);
    }

    public CoinShop()
    {
        coinInv = Bukkit.createInventory(null, 27, "CoinShop");
        initItemSetting();
        setItems();
        Thread coinStar = new Thread(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        StartCoin();
                    }
                }
        );
        if(CoinManager.isNull())
        {
            CoinManager.getInstance();
            coinStar.run();
        }
    }

    public static void setItems()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(invBasic[i][j] == 1)
                {
                    int index = i * 9 + j;
                    coinInv.setItem(index, ItemManager.gui_GrayGlassPane);
                }
                else if(invBasic[i][j] == 2)
                {
                    int index = i * 9 + j;
                    ItemStack indexItem = sellItem.poll();
                    ItemMeta itemMeta = indexItem.getItemMeta();
                    int price = shopConfig.getInt(itemNamePare.get(indexItem.getType()) + ".price");
                    List<String> lore = new ArrayList<>();
                    if(shopConfig.getBoolean(itemNamePare.get(indexItem.getType()) + ".delisting"))
                    {
                        lore.add(ChatColor.RED + "해당 코인은 사망했습니다...");
                    }
                    else
                    {
                        lore.add(ChatColor.GOLD + "현재가: " + price + " (0.0%)");
                        lore.add(ChatColor.WHITE + "좌클릭: 1개 구매");
                        lore.add(ChatColor.WHITE + "우클릭: 1개 판매");
                        lore.add(ChatColor.WHITE + "Shift + 우클릭: 전부 판매");
                    }
                    itemMeta.setLore(lore);
                    indexItem.setItemMeta(itemMeta);
                    coinInv.setItem(index, indexItem);
                }
            }
        }
    }

    public static void reloadAllItems()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(invBasic[i][j] == 1)
                {
                    int index = i * 9 + j;
                    coinInv.setItem(index, ItemManager.gui_GrayGlassPane);
                }
                else if(invBasic[i][j] == 2)
                {
                    int index = i * 9 + j;
                    ItemStack indexItem = sellItem.poll();
                    reloadItem(indexItem, itemNamePare.get(indexItem.getType()));
                    coinInv.setItem(index, indexItem);
                }
            }
        }
    }
    public static void reloadItem(ItemStack itemStack, String itemName)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();

        String delistingpath = itemName + ".delisting";
        String maxvariationpath = itemName + ".maxvariation";
        String pricepath = itemName + ".price";

        boolean isDelisting = shopConfig.getBoolean(delistingpath);
        List<String> lore = new ArrayList<>();

        if(isDelisting == false)
        {
            double maxVariation = shopConfig.getDouble(maxvariationpath);
            int randNum = random.nextInt(100) - 49;
            int price = shopConfig.getInt(pricepath);

            int variation = (int) Math.round(maxVariation * ((double)randNum / 100));

            double fluctuation = Math.round((double)variation / (double)price * 100.0 * 10.0) / 10.0;

            price = price + variation;

            if(price <= 0)
            {
                shopConfig.set(delistingpath, true);
                shopConfig.set(pricepath, 0);
                lore.add(ChatColor.RED + "해당 코인은 사망했습니다...");
                AllPlayerCoinZero(itemName);
                TestPlugin.getConfigManager().saveConfig(configName);
                return;
            }

            else if (fluctuation > 0)
            {
                lore.add(ChatColor.GREEN + "현재가: " + price + " (+" + fluctuation + "%)");
            }
            else if (fluctuation < 0)
            {
                lore.add(ChatColor.RED + "현재가: " + price + " (" + fluctuation + "%)");
            }
            else
            {
                lore.add(ChatColor.GOLD + "현재가: " + price + " (" + fluctuation + "%)");
            }
            lore.add(ChatColor.WHITE + "좌클릭: 1개 구매");
            lore.add(ChatColor.WHITE + "우클릭: 1개 판매");
            lore.add(ChatColor.WHITE + "Shift + 우클릭: 전부 판매");

            shopConfig.set(pricepath, price);

            TestPlugin.getConfigManager().saveConfig(configName);
        }
        else
        {
            lore.add(ChatColor.RED + "해당 코인은 사망했습니다...");

            Thread thread = new Thread(
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            CoinManager.DelistingCoinCount(itemName);
                        }
                    }
            );
            if(!coinDelistings.containsKey(itemName))
            {
                coinDelistings.put(itemName, true);
                thread.run();
            }
            TestPlugin.getConfigManager().saveConfig(configName);
        }


        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    private static void AllCoinSell()
    {
        Bukkit.broadcastMessage(ChatColor.AQUA + "오늘의 코인이 마감되었습니다.");
        HashMap<Player, Integer> sumPrice = new HashMap<>();
        itemNamePare.forEach((key, value) ->
        {
            String coinPath = "." + value;
            String coinName;
            switch (coinPath)
            {
                case ".pungsandog_coin":
                    coinName = CoinManager.PungsanDogCoinName;
                    break;
                case ".mole_coin":
                    coinName = CoinManager.MoleCoinName;
                    break;
                case ".beet_coin":
                    coinName = CoinManager.BeetCoinName;
                    break;
                case ".kimchi_coin":
                    coinName = CoinManager.KimchiCoinName;
                    break;
                default:
                    coinName = "???";
            }

            List<Player> playerList = (List<Player>) Bukkit.getOnlinePlayers();
            for(int i = 0; i < playerList.size(); i++)
            {
                Player player = playerList.get(i);
                Bukkit.broadcastMessage(player + "");
                int coinAmount = PlayerManager.GetCoin(player, coinPath);
                int coinPrice = shopConfig.getInt(value + ".price");
                if(coinAmount > 0)
                {
                    player.sendMessage(coinName + ChatColor.WHITE + " 코인의 수입은 " + ChatColor.GOLD + coinPrice * coinAmount + " 골드 " + ChatColor.WHITE + "입니다.");
                    PlayerManager.SellCoin(player, coinPath, coinAmount);
                    PlayerManager.AddGold(player, coinAmount * coinPrice);

                    if(!sumPrice.containsKey(player))
                    {
                        sumPrice.put(player, 0);
                    }
                    sumPrice.put(player, sumPrice.get(player) + coinAmount * coinPrice);
                }
            }
            for(int i = 0; i < Bukkit.getServer().getOfflinePlayers().length; i++)
            {
                Player player = Bukkit.getServer().getOfflinePlayers()[i].getPlayer();
                Bukkit.broadcastMessage(player + "");
                if(player == null)
                    return;
                int coinAmount = PlayerManager.GetCoin(player, coinPath);
                int coinPrice = shopConfig.getInt(value + ".price");
                if(coinAmount > 0)
                {
                    PlayerManager.SellCoin(player, coinPath, coinAmount);
                    PlayerManager.AddGold(player, coinAmount * coinPrice);

                    if(!sumPrice.containsKey(player))
                    {
                        sumPrice.put(player, 0);
                    }
                    sumPrice.put(player, sumPrice.get(player) + coinAmount * coinPrice);
                }
            }
        });
        sumPrice.forEach((coinPlayer, coinSum) ->
        {
            coinPlayer.sendMessage(ChatColor.AQUA + "오늘의 코인 총 수입은 " + ChatColor.GOLD + coinSum + " 골드 " + ChatColor.AQUA + "입니다.");
        });
        sumPrice.clear();
    }

    private static void AllPlayerCoinZero(String coinName)
    {
        String coinPath = "." + coinName;

        String coinDisplayName;
        switch (coinPath)
        {
            case ".pungsandog_coin":
                coinDisplayName = CoinManager.PungsanDogCoinName;
                break;
            case ".mole_coin":
                coinDisplayName = CoinManager.MoleCoinName;
                break;
            case ".beet_coin":
                coinDisplayName = CoinManager.BeetCoinName;
                break;
            case ".kimchi_coin":
                coinDisplayName = CoinManager.KimchiCoinName;
                break;
            default:
                coinDisplayName = "???";
        }
        List<Player> playerList = (List<Player>) Bukkit.getWorld("world").getPlayers();
        for(int i = 0; i < playerList.size(); i++)
        {
            Player player = playerList.get(i);
            Bukkit.broadcastMessage(player + "");
            int coinAmount = PlayerManager.GetCoin(player, coinPath);
            if(coinAmount > 0)
            {
                player.sendMessage(coinDisplayName + ChatColor.RED + " 이 상장폐지되었습니다.");
                PlayerManager.SetCoin(player, coinPath, 0);
            }
        }

        for(int i = 0; i < Bukkit.getServer().getOfflinePlayers().length; i++)
        {
            Player player = Bukkit.getServer().getOfflinePlayers()[i].getPlayer();
            Bukkit.broadcastMessage(player + "");
            if(player == null)
                return;
            int coinAmount = PlayerManager.GetCoin(player, coinPath);
            if(coinAmount > 0)
            {
                player.sendMessage(coinDisplayName + ChatColor.RED + " 이 상장폐지되었습니다.");
                PlayerManager.SetCoin(player, coinPath, 0);
            }
        }
    }

    public static Integer GetPrice(Material itemMaterial)
    {
        if(itemNamePare.containsKey(itemMaterial))
        {
            String itemName = itemNamePare.get(itemMaterial);
            String path = itemName + ".price";
            int price = shopConfig.getInt(path);
            return price;
        }
        return -2;
    }

    public void open(Player player)
    {
        player.openInventory(coinInv);
    }

    @Override
    public Inventory getInventory()
    {
        return coinInv;
    }
}
