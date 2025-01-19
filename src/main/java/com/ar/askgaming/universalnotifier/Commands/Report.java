package com.ar.askgaming.universalnotifier.Commands;

import java.util.HashMap;

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

    private HashMap<Player, Long> cooldown = new HashMap<>();

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
        Player player = (Player) sender;

        if (cooldown.containsKey(player)) {
            if (System.currentTimeMillis() - cooldown.get(player) < 60000*5) {
                sender.sendMessage("§cYou must wait 5 minute before sending another report");
                return true;
            }
        }
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        String report = "[REPORT] " + player.getName() + ": " + message.toString();
        player.sendMessage("§aReporte enviado correctamente");
        cooldown.put(player, System.currentTimeMillis());
        
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("universalnotifier.broadcast")) {
                p.sendMessage(report);;
            }
        }
        plugin.getNotification().broadcastToAll(Alert.COMMAND_REPORT, report);
        return true;
    }

}
