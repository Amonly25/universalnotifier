package com.ar.askgaming.universalnotifier;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class Commands implements TabExecutor {

    private UniversalNotifier plugin;

    public Commands(UniversalNotifier plugin){
        this.plugin = plugin;
        
        plugin.getServer().getPluginCommand("universalnotifier").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("§cUse /notifier discord/telegram/email/wsp <message>");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for(int i = 1; i < args.length; i++){
            message.append(args[i]).append(" ");
        }

        switch (args[0].toLowerCase()) {
            case "discord":
                plugin.getNotification().send(NotificationManager.Type.DISCORD, message.toString());
                break;
            case "telegram":
                plugin.getNotification().send(NotificationManager.Type.TELEGRAM, message.toString());
                break;
            case "email":
                plugin.getNotification().send(NotificationManager.Type.EMAIL, message.toString());
                break;
            case "wsp":
                plugin.getNotification().send(NotificationManager.Type.WHATSAPP, message.toString());
                break;
    
            default:
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of("discord", "telegram", "email", "wsp");
        }
        return null;
    }

}
