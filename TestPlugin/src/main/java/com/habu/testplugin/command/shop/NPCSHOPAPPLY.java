package com.habu.testplugin.command.shop;

import com.habu.testplugin.event.OPPlayerInteract;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class NPCSHOPAPPLY implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
            if(OPPlayerInteract.getSwitch() && args.length == 0)
            {
                OPPlayerInteract.Switch(false);
                OPPlayerInteract.setShopType("");
                sender.sendMessage("turn False");
            }
            else
            {
                if(args.length > 0)
                {
                    String shopType = args[0];

                    switch (shopType)
                    {
                        case "바다상점":
                            sender.sendMessage("바다 상점");
                            break;
                        case "광물상점":
                            sender.sendMessage("광물 상점");
                            break;
                        case "작물상점":
                            sender.sendMessage("작물 상점");
                            break;
                        case "나무상점":
                            sender.sendMessage("나무 상점");
                            break;
                        case "잡화상점":
                            sender.sendMessage("잡화 상점");
                            break;
                        case "랜덤스폰알상점":
                            sender.sendMessage("랜덤 스폰알 상점");
                            break;
                        case "낚시꾼전직":
                            sender.sendMessage("낚시꾼 전직");
                            break;
                        case "광부전직":
                            sender.sendMessage("광부 전직");
                            break;
                        case "농부전직":
                            sender.sendMessage("농부 전직");
                            break;
                        case "나무꾼전직":
                            sender.sendMessage("나무꾼 전직");
                            break;
                        case "사냥꾼전직":
                            sender.sendMessage("사냥꾼 전직");
                            break;
                        default:
                            sender.sendMessage("정확한 번호를 입력해주세요.");
                            return false;
                    }

                    OPPlayerInteract.Switch(true);
                    OPPlayerInteract.setShopType(shopType);
                    sender.sendMessage("turn True");
                }
                else
                {
                    sender.sendMessage("적용할 상점 유형을 입력해 주세요.");
                }
            }

        return false;
    }
}
