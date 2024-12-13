package com.ar.askgaming.universalnotifier.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;
import com.ar.askgaming.universalnotifier.Managers.NotificationManager.Type;

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
                plugin.getNotification().broadcastTo(Type.DISCORD, Alert.COMMAND_BROADCAST, message.toString());
                sender.sendMessage("Proccessing discord message request...");
                break;
            case "telegram":
                plugin.getNotification().broadcastTo(Type.TELEGRAM, Alert.COMMAND_BROADCAST, message.toString());
                sender.sendMessage("Proccessing telegram message request...");
                break;
            case "email":
                plugin.getNotification().broadcastTo(Type.EMAIL, Alert.COMMAND_BROADCAST, message.toString());
                sender.sendMessage("Proccessing email message request...");
                break;
            case "wsp":
                plugin.getNotification().broadcastTo(Type.WHATSAPP, Alert.COMMAND_BROADCAST, message.toString());
                sender.sendMessage("Proccessing whatsapp message request...");
                break;
    
            default:
                sender.sendMessage("§cUse /notifier discord/telegram/email/wsp <message>");
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return List.of("discord", "telegram", "email", "wsp");
        }
        return null;
    }

}
