package com.habu.testplugin.command.operatorcommand;

import com.habu.testplugin.Hidden.LightningCharger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GetWeapon implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        Player player = (Player) sender;
        if(args.length <= 0)
        {
            player.sendMessage("무기명을 입력해 주세요");
            return false;
        }

        ItemStack weapon;

        switch (args[0])
        {
            case "LightningCharger":
                weapon = LightningCharger.weapon;
                break;
            default:
                weapon = null;
                break;
        }

        player.getInventory().addItem(weapon);
        return false;
    }
}
