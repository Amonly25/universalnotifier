package com.ar.askgaming.universalnotifier.Managers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ar.askgaming.universalnotifier.UniversalNotifier;

public class AlertManager {

    private UniversalNotifier plugin;
    private File file;
    private FileConfiguration config;
    public FileConfiguration getConfig() {
        return config;
    }
    public enum Alert {
        SHUTDOWN,
        STARTUP,
        COMMAND,
        SPAWN_DRAGON,
        COMMAND_BROADCAST,
        COMMAND_REPORT,
        LOW_TPS,
        VERY_LOW_TPS,
        HAPPYHOUR_START,
        WARZONE_START,

    }
    public AlertManager(UniversalNotifier plugin){
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), "types.yml");

        if (!file.exists()) {
            plugin.saveResource("types.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
