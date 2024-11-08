package com.ar.askgaming.universalnotifier;

import java.io.IOException;

import javax.mail.MessagingException;

public class Notification {

    private UniversalNotifier plugin;

    public enum Type 
    {
        DISCORD,
        EMAIL,
        TELEGRAM,
        WHATSAPP
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
                // Send message to WhatsApp
                break;
            default:
                break;
        }
    }

    public Notification(UniversalNotifier plugin){
        this.plugin = plugin;
        
    }
}
