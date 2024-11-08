package com.ar.askgaming.universalnotifier.Types;

import com.ar.askgaming.universalnotifier.UniversalNotifier;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DiscordIntegration {
    
    private JDA jda;
    private String token;
    private UniversalNotifier plugin;
    private long channelId = 1304243175933870171L;

    public DiscordIntegration(UniversalNotifier plugin){
        this.plugin = plugin;

        token = plugin.getConfig().getString("discord.bot_token");
        
        try {
            // Inicializar JDA
            jda = JDABuilder.createDefault(token).build().awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel != null) {
            channel.sendMessage(message).queue();
        } else {
            plugin.getLogger().warning("No se encontró el canal de Discord con ID: " + channelId);
        }
    }
}
