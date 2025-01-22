package com.ar.askgaming.universalnotifier.Integrations;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.bukkit.configuration.ConfigurationSection;

import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;


public class Email {

    private String host;
    private String port;
    private String user;
    private String password;
    private List<String> emails = new ArrayList<>();
    private String subject;
    private String debug;
    private String timeout;
    private String ssl;

    private UniversalNotifier plugin;

    public Email(UniversalNotifier plugin) {
        
        this.plugin = plugin;
        
        this.host = plugin.getConfig().getString("email.host");
        this.port = plugin.getConfig().getString("email.port");
        this.user = plugin.getConfig().getString("email.username");
        this.password = plugin.getConfig().getString("email.password");
        this.subject = plugin.getConfig().getString("email.subject");
        this.debug = plugin.getConfig().getString("email.debug", "false");
        this.timeout = plugin.getConfig().getString("email.timeout", "5000");
        this.ssl = plugin.getConfig().getString("email.ssl");

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("email.list");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                key = key.replace("*", ".");
                emails.add(key);
            }
        }
    }
    public void searchAndSend(Alert type, String message) {
        for (String email : emails) {
            List<String> types = plugin.getConfig().getStringList("email.list."+ email.replace(".", "*") +".types");
            if (types.contains(type.toString())) {
                send(email, message);
            }
        }
    }
    public void send(String email, String messageBody) {
        // Configuración de las propiedades del servidor SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", Boolean.toString(Boolean.parseBoolean(ssl)));
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port); // Puerto para TLS
        properties.put("mail.smtp.connectiontimeout", parseTimeout(timeout, "5000")); // valor predeterminado 5000 ms
        properties.put("mail.smtp.timeout", parseTimeout(timeout, "5000"));
        properties.put("mail.smtp.writetimeout", parseTimeout(timeout, "5000"));
        properties.put("mail.debug", debug); // Habilitar depuración
        // Crear una sesión de correo
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(messageBody);

            try (Transport transport = session.getTransport("smtp")) {
                transport.connect(host, user, password);
                transport.sendMessage(message, message.getAllRecipients());
                plugin.getLogger().info("Email sent to " + email);
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to send email to " + email + ": " + e.getMessage());
        } 
    }  
    private String parseTimeout(String value, String defaultValue) {
        try {
            return String.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
