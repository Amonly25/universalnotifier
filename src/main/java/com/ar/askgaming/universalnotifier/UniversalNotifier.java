package com.ar.askgaming.universalnotifier;

import org.bukkit.plugin.java.JavaPlugin;

public class UniversalNotifier extends JavaPlugin{
    
    DiscordIntegration discordIntegration;

    public void onEnable(){
        
        saveDefaultConfig();

        discordIntegration = new DiscordIntegration(this);

        getServer().getPluginCommand("universalnotifier").setExecutor(new Commands(this));
    }

    public void onDisable(){
        
    }

    public DiscordIntegration getDiscordIntegration() {
        return discordIntegration;
    }
}