package com.habu.testplugin.event;

import com.habu.testplugin.TestPlugin;
import com.habu.testplugin.db.player_db_connect;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class UsingCheckEvent implements Listener
{
    String checkId = "customitems:check";

    @EventHandler
    public void UseCheck(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Action action = event.getAction();
        ItemStack itemStack = player.getItemInHand();

        if(action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR))
        {
            CustomStack stack = CustomStack.byItemStack(itemStack);

            if(stack != null)
            {
                String itemId = stack.getNamespacedID();
                if(itemId.equals(checkId))
                {
                    String strAmount = itemStack.getLore().get(0);
                    strAmount = strAmount.replace("금액: ", "");
                    int amount = Integer.parseInt(strAmount);
                    TestPlugin.db_conn.AddGold(player, amount);
                    player.sendMessage(amount + "골드가 정상 입금되었습니다.");
                    if(itemStack.getAmount() >= 1)
                    {
                        int itemAmount = itemStack.getAmount() - 1;
                        itemStack.setAmount(itemAmount);
                    }
                    else
                    {
                        player.getInventory().removeItem(itemStack);
                    }
                }
            }
        }
    }
}
