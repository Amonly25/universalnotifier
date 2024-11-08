package com.ar.askgaming.universalnotifier;

import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.universalnotifier.Types.DiscordIntegration;
import com.ar.askgaming.universalnotifier.Types.EmailIntegration;
import com.ar.askgaming.universalnotifier.Types.TelegramIntegration;
import com.ar.askgaming.universalnotifier.Types.WhastappIntegration;


public class UniversalNotifier extends JavaPlugin{
    
    DiscordIntegration discordIntegration;
    TelegramIntegration telegramIntegration;
    EmailIntegration emailIntegration;
    WhastappIntegration whastappIntegration;

    Notification notification;

    public void onEnable(){
        
        saveDefaultConfig();

        discordIntegration = new DiscordIntegration(this);
        telegramIntegration = new TelegramIntegration(this);
        emailIntegration = new EmailIntegration(this);
        whastappIntegration = new WhastappIntegration(this);
        notification = new Notification(this);

        new Commands(this);
    }

    public void onDisable(){
        
    }

    public DiscordIntegration getDiscordIntegration() {
        return discordIntegration;
    }
    public Notification getNotification() {
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
}