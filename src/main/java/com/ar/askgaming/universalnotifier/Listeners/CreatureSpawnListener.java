package com.ar.askgaming.universalnotifier.Listeners;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;

public class CreatureSpawnListener implements Listener{

    private UniversalNotifier plugin;
    public CreatureSpawnListener(UniversalNotifier plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        
        LivingEntity entity = event.getEntity();
        if (entity instanceof EnderDragon){
            plugin.getNotification().broadcastToAll(Alert.SPAWN_DRAGON);
        }
    }
}
