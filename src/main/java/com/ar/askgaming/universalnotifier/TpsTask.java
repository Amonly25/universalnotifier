package com.ar.askgaming.universalnotifier;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;

public class TpsTask extends BukkitRunnable{
    private double low;
    private double veryLow;
    private int interval;
    private UniversalNotifier plugin;
    public TpsTask(UniversalNotifier plugin) {
        this.plugin = plugin;

        low = plugin.getConfig().getDouble("tps.low",16.0);
        veryLow = plugin.getConfig().getDouble("tps.very_low",12.0);
        interval = plugin.getConfig().getInt("tps.interval", 20);

    }

    private long lastNotification = 0;

    @Override
    public void run() {

        if (System.currentTimeMillis() - lastNotification < 60000*interval) {
            return;
        }

        double[] tps = Bukkit.getTPS();

        if (tps[0] < veryLow) {
            plugin.getNotification().broadcastToAll(Alert.VERY_LOW_TPS);
            lastNotification = System.currentTimeMillis();
            return;

        } else if (tps[0] < low) {
            plugin.getNotification().broadcastToAll(Alert.LOW_TPS);
            lastNotification = System.currentTimeMillis();
        }
    }
}
