package com.ar.askgaming.universalnotifier.Types;

import java.io.IOException;
import java.util.List;

import com.ar.askgaming.universalnotifier.NotificationManager.Alert;
import com.ar.askgaming.universalnotifier.UniversalNotifier;

import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TelegramIntegration {

    private UniversalNotifier plugin;

    private final String API_URL;
    private String botToken;
    private final OkHttpClient httpClient;
    private List<String> chatList;

    public TelegramIntegration(UniversalNotifier plugin) {
        this.plugin = plugin;
        this.botToken = plugin.getConfig().getString("telegram.bot_token");
        this.httpClient = new OkHttpClient();
        API_URL = "https://api.telegram.org/bot";
        chatList = plugin.getConfig().getStringList("telegram.chats_id");
    }

    public void send(Alert alert) throws IOException {

        for (String chatId : chatList) {
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
                    plugin.getLogger().warning("Error sending message to chat: " + chatId);
                    throw new IOException("Unexpected code " + response);
                }
                plugin.getLogger().info("Message sent to chat: " + chatId);
            }
        }
    }
}
