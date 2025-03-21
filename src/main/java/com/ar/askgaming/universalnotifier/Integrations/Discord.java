package com.ar.askgaming.universalnotifier.Integrations;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;

public class Discord {
    
    private final UniversalNotifier plugin;
    private JDA jda;
    private String token;
    private List<String> chatList = new ArrayList<>();
    private String activity = "";

    public Discord(UniversalNotifier plugin){
        this.plugin = plugin;

        loadConfig();

    }
    public void loadConfig(){

        token = plugin.getConfig().getString("discord.bot_token");
        activity = plugin.getConfig().getString("discord.activity");

        if (token == null || token.isEmpty()) {
            Bukkit.getLogger().severe("No Discord bot token found in config.yml! Disabling Discord integration.");
            return;
        }

        chatList.clear();
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("discord.channels_id");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                chatList.add(key);
            }
        }
        shutdown();

        try {
            jda = JDABuilder.createDefault(token).setActivity(Activity.playing(activity)).build().awaitReady();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Mantiene el estado de interrupción
            Bukkit.getLogger().severe("Thread was interrupted while initializing Discord bot.");
        } catch (InvalidTokenException e) {
            Bukkit.getLogger().severe("Invalid bot token! Please check your configuration.");
        } catch (Exception e) {
            Bukkit.getLogger().severe("Unexpected error initializing Discord bot: " + e.getMessage());
        }
    }

    public void sendMessage(String channelId, String message) {
        try {
            TextChannel channel = jda.getTextChannelById(channelId);
            if (channel != null) {
                channel.sendMessage(message).queue();
            } else {
                Bukkit.getLogger().warning("TextChannel with ID " + channelId + " not found");
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error sending message to Discord: " + e.getMessage());
        }
    }
    public void searchAndSend(Alert alert, String message) {
        String alertType = alert.toString();
        
        for (String chatId : chatList) {
            List<String> types = plugin.getConfig().getStringList("discord.channels_id." + chatId + ".types");
    
            if (types.contains(alertType)) {
                sendMessage(chatId, message);
            }
        }
    }
    public void shutdown() {
        if (jda != null && jda.getStatus() != JDA.Status.SHUTDOWN) {
            jda.shutdown();
        }
    }    
}
