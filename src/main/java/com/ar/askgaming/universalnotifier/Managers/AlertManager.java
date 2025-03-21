package com.ar.askgaming.universalnotifier.Managers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ar.askgaming.universalnotifier.UniversalNotifier;

public class AlertManager {

    private File file;
    private FileConfiguration config;

    public enum Alert {
        SHUTDOWN,
        STARTUP,
        COMMAND_BROADCAST,
        COMMAND_REPORT,
        LOW_TPS,
        VERY_LOW_TPS,
        CUSTOM

    }
    public AlertManager(UniversalNotifier plugin){

        file = new File(plugin.getDataFolder(), "types.yml");

        if (!file.exists()) {
            plugin.saveResource("types.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }
    public FileConfiguration getConfig() {
        return config;
    }
    
}
