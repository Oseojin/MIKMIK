package com.habu.testplugin.command;

import com.habu.testplugin.manager.PlayerManager;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

public class IssuingCheckCommand implements CommandExecutor
{
    static String checkId = "customitems:check";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if(args.length == 0)
        {
            player.sendMessage("금액을 입력해주세요. ex) /수표 100");
            return false;
        }

        int amount = Integer.parseInt(args[0]);

        if(PlayerManager.GetGold(player) >= amount)
        {
            CustomStack stack = CustomStack.getInstance(checkId);
            ItemStack itemStack = stack.getItemStack();
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setLore(Arrays.asList("금액: " + amount));
            itemStack.setItemMeta(itemMeta);

            player.getInventory().addItem(itemStack);
            PlayerManager.UseGold(player ,amount);
            player.sendMessage(amount + "골드 수표가 발행되었습니다.");
        }
        else
        {
            player.sendMessage("금액이 부족합니다.");
        }

        return false;
    }

    public static ItemStack IssuingCheck(int amount)
    {
        CustomStack stack = CustomStack.getInstance(checkId);
        ItemStack itemStack = stack.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(Arrays.asList("금액: " + amount));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
