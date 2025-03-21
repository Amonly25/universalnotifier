package com.ar.askgaming.universalnotifier;

import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.universalnotifier.Commands.Commands;
import com.ar.askgaming.universalnotifier.Commands.Report;
import com.ar.askgaming.universalnotifier.Integrations.Discord;
import com.ar.askgaming.universalnotifier.Integrations.Email;
import com.ar.askgaming.universalnotifier.Integrations.Telegram;
import com.ar.askgaming.universalnotifier.Managers.AlertManager;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;
import com.ar.askgaming.universalnotifier.Managers.NotificationManager;

public class UniversalNotifier extends JavaPlugin{
    
    private Discord discordIntegration;
    private Telegram telegramIntegration;
    private Email emailIntegration;

    private NotificationManager notification;
    private AlertManager alertManager;

    private static UniversalNotifier instance;

    public void onEnable(){
        instance = this;
        saveDefaultConfig();

        discordIntegration = new Discord(this);
        telegramIntegration = new Telegram(this);
        emailIntegration = new Email(this);
        notification = new NotificationManager(this);
        alertManager = new AlertManager(this);

        new Commands(this);
        new Report(this);

        notification.broadcastToAll(Alert.STARTUP,null);

        new TpsTask(this).runTaskTimer(this, 0, 20);
    }

    public void onDisable(){
        notification.broadcastToAll(Alert.SHUTDOWN,null);
        discordIntegration.shutdown();
        telegramIntegration.shutdown();
    }

    public Discord getDiscordIntegration() {
        return discordIntegration;
    }
    public NotificationManager getNotificationManager() {
        return notification;
    }
    public Telegram getTelegramIntegration() {
        return telegramIntegration;
    }
    public Email getEmailIntegration() {
        return emailIntegration;
    }

    public AlertManager getAlertManager() {
        return alertManager;
    }
    public static UniversalNotifier getInstance() {
        return instance;
    }
}