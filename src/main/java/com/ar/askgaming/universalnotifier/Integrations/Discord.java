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

public class Discord {
    
    private JDA jda;
    private final String token;
    private String guildId;
    private final UniversalNotifier plugin;
    private final List<String> chatList = new ArrayList<>();
    private String activity = "";

    public Discord(UniversalNotifier plugin){
        this.plugin = plugin;

        token = plugin.getConfig().getString("discord.bot_token");
        guildId = plugin.getConfig().getString("discord.guild_id");
        activity = plugin.getConfig().getString("discord.activity");

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("discord.channels_id");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                chatList.add(key);
            }
        }

        try {
            jda = JDABuilder.createDefault(token)
                    .setActivity(Activity.playing(activity))
                    .build().awaitReady();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void sendMessage(String channelId, String message) {
        try {
            TextChannel channel = jda.getTextChannelById(channelId);
            if (channel != null) {
                channel.sendMessage(message).queue();
            } else {
                Bukkit.getLogger().warning("TextChannel with ID " + channelId + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized void searchAndSend(Alert alert, String message) {
        for (String chatId : chatList) {
            
            List<String> types = plugin.getConfig().getStringList("discord.channels_id." + chatId + ".types");
            if (types.contains(alert.toString())) {
                sendMessage(chatId, message);
            }
        }
    }
    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
        }
    }    
}
