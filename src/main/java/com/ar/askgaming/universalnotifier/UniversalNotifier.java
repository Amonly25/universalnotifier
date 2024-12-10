package com.ar.askgaming.universalnotifier;

import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.universalnotifier.Managers.ConfigTypesManager;
import com.ar.askgaming.universalnotifier.NotificationManager.Alert;
import com.ar.askgaming.universalnotifier.Types.DiscordIntegration;
import com.ar.askgaming.universalnotifier.Types.EmailIntegration;
import com.ar.askgaming.universalnotifier.Types.TelegramIntegration;
import com.ar.askgaming.universalnotifier.Types.WhastappIntegration;


public class UniversalNotifier extends JavaPlugin{
    
    private DiscordIntegration discordIntegration;
    private TelegramIntegration telegramIntegration;
    private EmailIntegration emailIntegration;
    private WhastappIntegration whastappIntegration;

    private NotificationManager notification;
    private ConfigTypesManager configTypesManager;

    public void onEnable(){
        
        saveDefaultConfig();

        discordIntegration = new DiscordIntegration(this);
        telegramIntegration = new TelegramIntegration(this);
        emailIntegration = new EmailIntegration(this);
        whastappIntegration = new WhastappIntegration(this);
        notification = new NotificationManager(this);
        configTypesManager = new ConfigTypesManager(this);

        new Commands(this);

        notification.send(Alert.STARTUP);
    }

    public void onDisable(){
        getNotification().send(Alert.SHUTDOWN);
    }

    public DiscordIntegration getDiscordIntegration() {
        return discordIntegration;
    }
    public NotificationManager getNotification() {
        return notification;
    }
    public TelegramIntegration getTelegramIntegration() {
        return telegramIntegration;
    }
    public EmailIntegration getEmailIntegration() {
        return emailIntegration;
    }
    public WhastappIntegration getWhastappIntegration() {
        return whastappIntegration;
    }
    public ConfigTypesManager getConfigTypesManager() {
        return configTypesManager;
    }
}