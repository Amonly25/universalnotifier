package com.ar.askgaming.universalnotifier.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;
import com.ar.askgaming.warzone.CustomEvents.WarzoneStartEvent;

public class WarzoneListener implements Listener {

    private UniversalNotifier plugin;

    public WarzoneListener(UniversalNotifier plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("WarzoneListener registered");
    }

    @EventHandler
    public void onWarzoneStart(WarzoneStartEvent event) {
        plugin.getNotification().broadcastToAll(Alert.WARZONE_START, null);

    }
}
