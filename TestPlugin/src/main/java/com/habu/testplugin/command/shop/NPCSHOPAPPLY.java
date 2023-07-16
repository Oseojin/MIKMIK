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
                OPPlayerInteract.setJobNum(0);
                sender.sendMessage("turn False");
            }
            else
            {
                if(args.length > 0)
                {
                    int jobNum = Integer.parseInt(args[0]);

                    switch (jobNum)
                    {
                        case 1:
                            sender.sendMessage("낚시꾼");
                            break;
                        case 2:
                            sender.sendMessage("광부");
                            break;
                        case 3:
                            sender.sendMessage("농부");
                            break;
                        case 4:
                            sender.sendMessage("나무꾼");
                            break;
                        case 5:
                            sender.sendMessage("일반");
                            break;
                        case 11:
                            sender.sendMessage("낚시꾼 전직");
                            break;
                        case 22:
                            sender.sendMessage("광부 전직");
                            break;
                        case 33:
                            sender.sendMessage("농부 전직");
                            break;
                        case 44:
                            sender.sendMessage("나무꾼 전직");
                            break;
                        default:
                            sender.sendMessage("정확한 번호를 입력해주세요.");
                            return false;
                    }

                    OPPlayerInteract.Switch(true);
                    OPPlayerInteract.setJobNum(jobNum);
                    sender.sendMessage("turn True");
                }
                else
                {
                    sender.sendMessage("적용할 직업 번호를 입력해 주세요.");
                }
            }

        return false;
    }
}
