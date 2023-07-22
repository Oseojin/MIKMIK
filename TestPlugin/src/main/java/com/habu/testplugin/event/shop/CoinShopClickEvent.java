package com.habu.testplugin.event.shop;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.manager.CoinManager;
import com.habu.testplugin.manager.ItemManager;
import com.habu.testplugin.manager.PlayerManager;
import com.habu.testplugin.shop.CoinShop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class CoinShopClickEvent implements Listener
{
    private static HashMap<Material, String> itemMap = new HashMap<Material, String>()
    {{
        put(Material.BONE, "pungsandog_coin");
        put(Material.DIAMOND_SHOVEL, "mole_coin");
        put(Material.BEETROOT, "beet_coin");
        put(Material.ROTTEN_FLESH, "kimchi_coin");
    }};

    @EventHandler
    public void onClickShop(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if(inv == null)
            return;

        String title = ChatColor.stripColor(event.getView().getTitle());

        if(title.equalsIgnoreCase("CoinShop"))
        {
            if(inv.equals(player.getInventory()))
            {
                if(event.isLeftClick())
                {
                    event.setCancelled(true);
                    return;
                }
            }
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if(clickedItem == null)
                return;
            if (!clickedItem.equals(ItemManager.gui_GrayGlassPane))
            {
                Material material = clickedItem.getType();
                int price = CoinShop.GetPrice(material);
                if(event.isLeftClick() && !event.isShiftClick())
                {
                    PurchaseItem(player, material, price, false);
                }
                else if(event.isLeftClick() && event.isShiftClick())
                {
                    PurchaseItem(player, material, price, true);
                }
                else if(event.isRightClick() && !event.isShiftClick())
                {
                    SellItem(player,material, price, PlayerManager.GetCoin(player, itemMap.get(clickedItem.getType())), false);
                }
                else if(event.isRightClick() && event.isShiftClick())
                {
                    SellItem(player, material, price, PlayerManager.GetCoin(player, itemMap.get(clickedItem.getType())), true);
                }
            }
        }
    }

    private void PurchaseItem(Player player, Material material, Integer price, Boolean purchaseTen)
    {
        String coinPath = "." + itemMap.get(material);

        if(!CoinShop.GetOpen())
        {
            player.sendMessage(ChatColor.RED + "코인 거래 가능 시간이 아닙니다!");
            return;
        }

        int amount = 1;
        if(purchaseTen)
        {
            amount = 10;
        }

        if(PlayerManager.GetGold(player) < price * amount)
        {
            player.sendMessage("돈이 부족합니다.");
            return;
        }

        if(CoinShop.shopConfig.getBoolean(itemMap.get(material) + ".delisting"))
        {
            player.sendMessage("상장폐지된 코인은 구매할 수 없습니다.");
            return;
        }

        PlayerManager.AddCoin(player, coinPath, amount);
        PlayerManager.UseGold(player, price * amount);

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
        player.sendMessage( coinName + ChatColor.WHITE + " " + amount + " 개를 " + price * amount + " 골드에 구매하였습니다.");
        player.sendMessage("현재 " + coinName + ChatColor.WHITE + " 보유 개수: " + PlayerManager.GetCoin(player, coinPath));
    }

    private void SellItem(Player player, Material material, Integer price, Integer playerAmount, Boolean sellAll)
    {
        String coinPath = "." + itemMap.get(material);

        if(!CoinShop.GetOpen())
        {
            player.sendMessage(ChatColor.RED + "코인 거래 가능 시간이 아닙니다!");
            return;
        }

        int amount = 1;

        if(sellAll)
        {
            amount = PlayerManager.GetCoin(player, coinPath);
        }

        if(playerAmount <= 0)
        {
            player.sendMessage(ChatColor.RED + "해당 코인을 보유하고있지 않습니다!");
            return;
        }

        PlayerManager.SellCoin(player, coinPath, amount);
        PlayerManager.AddGold(player, price * amount);

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
        player.sendMessage( coinName + ChatColor.WHITE + "를 " + price * amount + " 골드에 판매하였습니다.");
        player.sendMessage("현재 " + coinName + ChatColor.WHITE + " 보유 개수: " + PlayerManager.GetCoin(player, coinPath));
    }
}
