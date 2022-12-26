package com.qatros.binamurid.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class testing extends AppCompatActivity {

    private OkHttpClient client;

    private final class echoWebSocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("HEllo");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {

        }
    }

    private void start() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://bina-murid.fly.dev/api/v1/cable").build();
        echoWebSocketListener listener = new echoWebSocketListener();
        client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }
}
