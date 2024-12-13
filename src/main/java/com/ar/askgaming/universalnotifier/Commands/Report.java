package com.ar.askgaming.universalnotifier.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;

public class Report implements CommandExecutor{

    private UniversalNotifier plugin;
    public Report(UniversalNotifier plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginCommand("report").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUse /report <message>");
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou must be a player to use this command");
            return true;
        }
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }
        Player player = (Player) sender;
        String report = "[REPORT] " + player.getName() + ": " + message.toString();
        Bukkit.broadcast(report, "universalnotifier.broadcast");
        plugin.getNotification().broadcastToAll(Alert.COMMAND_REPORT, report);
        return true;
    }

}
