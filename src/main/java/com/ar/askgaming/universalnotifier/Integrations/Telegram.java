package com.ar.askgaming.universalnotifier.Integrations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.json.JSONObject;

import com.ar.askgaming.universalnotifier.UniversalNotifier;
import com.ar.askgaming.universalnotifier.Managers.AlertManager.Alert;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Telegram {

    private UniversalNotifier plugin;

    private final String API_URL;
    private String botToken;
    private final OkHttpClient httpClient;
    private List<String> chatList = new ArrayList<>();

    public Telegram(UniversalNotifier plugin) {
        this.plugin = plugin;
        this.botToken = plugin.getConfig().getString("telegram.bot_token");
        this.httpClient = new OkHttpClient();
        API_URL = "https://api.telegram.org/bot";
        
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("telegram.chats_id");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                chatList.add(key);
            }
        }
    }

    public void searchAndSend(Alert alert, String message) {
        for (String chatId : chatList) {
            if (plugin.getConfig().getStringList("telegram.chats_id."+ chatId+".types").contains(alert.toString())) {
                try {
                    sendMessage(chatId, message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendMessage(String chatId, String message) throws IOException {

        JSONObject json = new JSONObject();
        json.put("chat_id", chatId);
        json.put("text", message);

        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        // Construir la solicitud POST
        Request request = new Request.Builder()
                .url(API_URL + botToken + "/sendMessage")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                plugin.getLogger().warning("Failed to send message to chat: " + chatId + " | Response code: " + response.code());
            }
            plugin.getLogger().info("Message sent to chat: " + chatId);
        }
    }
    public void shutdown() {
        if (httpClient != null && httpClient.connectionPool() != null) {
            httpClient.connectionPool().evictAll(); // Limpia conexiones activas
            httpClient.dispatcher().executorService().shutdown(); // Cierra subprocesos
        }
    }
}
