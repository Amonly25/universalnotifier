package com.ar.askgaming.universalnotifier;

import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.universalnotifier.Commands.Commands;
import com.ar.askgaming.universalnotifier.Commands.Report;
import com.ar.askgaming.universalnotifier.Integrations.Discord;
import com.ar.askgaming.universalnotifier.Integrations.Email;
import com.ar.askgaming.universalnotifier.Integrations.Telegram;
import com.ar.askgaming.universalnotifier.Integrations.Whatsapp;
import com.ar.askgaming.universalnotifier.Listeners.CreatureSpawnListener;
import com.ar.askgaming.universalnotifier.Managers.AlertManager;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;
import com.ar.askgaming.universalnotifier.Managers.NotificationManager;


public class UniversalNotifier extends JavaPlugin{
    
    private Discord discordIntegration;
    private Telegram telegramIntegration;
    private Email emailIntegration;
    private Whatsapp whastappIntegration;

    private NotificationManager notification;
    private AlertManager alertManager;

    public void onEnable(){
        
        saveDefaultConfig();

        discordIntegration = new Discord(this);
        telegramIntegration = new Telegram(this);
        emailIntegration = new Email(this);
        notification = new NotificationManager(this);
        alertManager = new AlertManager(this);

        new Commands(this);
        new Report(this);

        new CreatureSpawnListener(this);

        notification.broadcastToAll(Alert.STARTUP,null);

        new TpsTask(this).runTaskTimer(this, 0, 20);
    }

    public void onDisable(){
        getNotification().broadcastToAll(Alert.SHUTDOWN,null);
        discordIntegration.shutdown();
    }

    public Discord getDiscordIntegration() {
        return discordIntegration;
    }
    public NotificationManager getNotification() {
        return notification;
    }
    public Telegram getTelegramIntegration() {
        return telegramIntegration;
    }
    public Email getEmailIntegration() {
        return emailIntegration;
    }
    public Whatsapp getWhastappIntegration() {
        return whastappIntegration;
    }
    public AlertManager getAlertManager() {
        return alertManager;
    }
}