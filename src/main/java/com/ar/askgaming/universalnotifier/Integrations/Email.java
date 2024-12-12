package com.ar.askgaming.universalnotifier.Integrations;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ar.askgaming.universalnotifier.UniversalNotifier;


public class Email {

    private String host;
    private String port;
    private String user;
    private String password;
    private List<String> to;
    private String subject;
    private Boolean debug;
    private String timeout;
    private Boolean ssl;

    private UniversalNotifier plugin;

    public Email(UniversalNotifier plugin) {
        
        this.plugin = plugin;
        
        this.host = plugin.getConfig().getString("email.host");
        this.port = plugin.getConfig().getString("email.port");
        this.user = plugin.getConfig().getString("email.username");
        this.password = plugin.getConfig().getString("email.password");
        this.to = plugin.getConfig().getStringList("email.list");
        this.subject = plugin.getConfig().getString("email.subject");
        this.debug = plugin.getConfig().getBoolean("email.debug");
        this.timeout = plugin.getConfig().getString("email.timeout");
        this.ssl = plugin.getConfig().getBoolean("email.ssl");
    }

    public void send(String messageBody) {
                // Configuración de las propiedades del servidor SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", ssl); // Usar SSL en lugar de STARTTLS
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port); // Puerto para TLS
        properties.put("mail.smtp.connectiontimeout", timeout); // 5 segundos de timeout
        properties.put("mail.smtp.timeout", timeout); // 5 segundos de timeout
        properties.put("mail.smtp.writetimeout", timeout);
        properties.put("mail.debug", debug); // Habilitar depuración
        // Crear una sesión de correo
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        session.setDebug(true);

        for (String email : to) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(subject);
                message.setText(messageBody);

                // Enviar el mensaje
                Transport.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }  
    }  
}
