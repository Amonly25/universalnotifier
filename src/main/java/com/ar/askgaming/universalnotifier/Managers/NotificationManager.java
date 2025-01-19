package com.ar.askgaming.universalnotifier.Managers;

import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;

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

    public void send(Type type, Alert alert, String message){

        switch (type) {
            case DISCORD:
                plugin.getDiscordIntegration().searchAndSend(alert, message);
                break;
            case EMAIL:
                plugin.getEmailIntegration().searchAndSend(alert, message);              
                break;
            case TELEGRAM:
                plugin.getTelegramIntegration().searchAndSend(alert, message);
                break;
            // case WHATSAPP:
            //     plugin.getWhastappIntegration().searchAndSend(alert, message);
            //     break;
            default:
                break;
        }
    }

    public void broadcastToAll(Alert alert, String message){
        if (message == null) {
            message = plugin.getAlertManager().getConfig().getString(alert.toString() + ".message","No message found for alert: " + alert.toString());

        }
        for (Type type : Type.values()) {
            send(type, alert, message);
        }
    }
    public void broadcastTo(Type type, Alert alert, String message){
        send(type, alert, message);
    }
}
