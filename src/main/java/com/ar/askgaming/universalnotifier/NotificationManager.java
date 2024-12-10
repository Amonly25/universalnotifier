package com.ar.askgaming.universalnotifier;

import java.io.IOException;
import java.util.List;

public class NotificationManager {

    private UniversalNotifier plugin;

    public NotificationManager(UniversalNotifier plugin){
        this.plugin = plugin;
        
    }
    public enum Type {
        DISCORD,
        EMAIL,
        TELEGRAM,
        WHATSAPP
    }

    public enum Alert {
        SHUTDOWN,
        STARTUP
    }

    public void send(Type type, String message){

        switch (type) {
            case DISCORD:
                try {
                    plugin.getDiscordIntegration().sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case EMAIL:
                new Thread(() -> {
                    plugin.getEmailIntegration().send(message); // Método que envía el correo
                }).start();
              
                break;
            case TELEGRAM:
                try {
                    plugin.getTelegramIntegration().send(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case WHATSAPP:
                plugin.getWhastappIntegration().send(message);
                break;
            default:
                break;
        }
    }

    public boolean isEnable(Type type, Alert alert){
        List<String> alerts = plugin.getConfig().getStringList(type.toString().toLowerCase() + ".alerts_types");

        if (alerts == null) return false;
        return alerts.contains(alert.toString());
    }
    public void send(Alert alert){
        for (Type type : Type.values()) {
            if (isEnable(type, alert)) {
                String message = plugin.getConfigTypesManager().getConfig().getString(alert.toString() + ".message");
                send(type, message);
            }
        }
    }
}
