package com.example.server.WebSocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        Map<String, String> value = new Gson().fromJson(message.getPayload(), Map.class);

        // for (WebSocketSession webSocketSession : sessions) {
            // Map value = new Gson().fromJson(message.getPayload(), Map.class);
            // webSocketSession.sendMessage(new TextMessage(new Gson().toJson(value.get("name"))));
        // }

        session.sendMessage(new TextMessage(new Gson().toJson(value.get("name"))));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // the messages will be broadcasted to all users.
        sessions.add(session);
    }

}