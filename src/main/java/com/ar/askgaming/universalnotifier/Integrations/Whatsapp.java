package com.ar.askgaming.universalnotifier.Integrations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.ar.askgaming.universalnotifier.UniversalNotifier;

public class Whatsapp {

    private String token;
    private String phone;
    private String urlApi;
    private List<String> numbers;
    private UniversalNotifier plugin;

    public Whatsapp(UniversalNotifier plugin){
        this.plugin = plugin;

        this.token = plugin.getConfig().getString("whatsapp.api_token");
        this.phone = plugin.getConfig().getString("whatsapp.api_number_id");
        this.numbers = plugin.getConfig().getStringList("whatsapp.numbers");

        urlApi = "https://graph.facebook.com/v15.0/" + phone + "/messages";
    }

    public void send(String message) {
        for (String n : numbers){
            try {
                // Crear la URL de la solicitud
                @SuppressWarnings("deprecation")
                URL url = new URL(urlApi);
    
                plugin.getLogger().info(urlApi);
    
                // Crear la conexión HTTP
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
    
                String jsonBody = "{"
                + "\"messaging_product\": \"whatsapp\","
                + "\"to\": \"" + n + "\","
                + "\"type\": \"template\","
                + "\"template\": {"
                + "\"name\": \"notificacion\","
                + "\"language\": {\"code\": \"es_AR\"},"
                + "\"components\": [{"
                + "\"type\": \"body\","
                + "\"parameters\": [{"
                + "\"type\": \"text\","
                + "\"text\": \"" + message + "\""
                + "}]"
                + "}]"
                + "}"
                + "}";
    
            // Enviar la solicitud
            connection.getOutputStream().write(jsonBody.getBytes("UTF-8"));
    
            // Obtener el código de respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta de la API
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("API Response: " + response.toString());
                } catch (IOException e) {
                    System.err.println("Error al leer la respuesta de la API");
                    e.printStackTrace();
                }
                System.out.println("Mensaje enviado exitosamente.");
            } else {
                // Leer la respuesta de error si la respuesta no es OK
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String inputLine;
                    StringBuilder errorResponse = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        errorResponse.append(inputLine);
                    }
                    System.out.println("Error response: " + errorResponse.toString());
                }
                System.out.println("Error al enviar mensaje: " + responseCode);
            }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
