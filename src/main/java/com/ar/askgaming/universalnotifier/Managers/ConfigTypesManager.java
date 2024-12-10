package com.ar.askgaming.universalnotifier.Managers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ar.askgaming.universalnotifier.UniversalNotifier;

public class ConfigTypesManager {

    private UniversalNotifier plugin;
    private File file;
    private FileConfiguration config;
    public FileConfiguration getConfig() {
        return config;
    }
    public ConfigTypesManager(UniversalNotifier plugin){
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
