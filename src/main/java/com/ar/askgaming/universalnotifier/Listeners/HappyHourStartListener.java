package com.ar.askgaming.universalnotifier.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.ar.askgaming.happyhour.CustomEvent.HappyHourStartEvent;
import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;

public class HappyHourStartListener implements Listener{

    private UniversalNotifier plugin;
    public HappyHourStartListener(UniversalNotifier plugin) {
        this.plugin = plugin;

    }
    @EventHandler
    public void onHappyHourStart(HappyHourStartEvent event) {

        String mode = event.getHappyHour().getDisplayName();
        String message = plugin.getAlertManager().getConfig().getString(Alert.HAPPYHOUR_START.toString() + ".message","No message found for alert: " + Alert.HAPPYHOUR_START.toString());
        message = message.replace("{mode}", mode);
        plugin.getNotification().broadcastToAll(Alert.HAPPYHOUR_START,message);
    }

}
