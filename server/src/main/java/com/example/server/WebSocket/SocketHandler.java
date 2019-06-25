package com.example.server.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    int messagecount = 0;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        // parse message
        Map<String, String> value = new Gson().fromJson(message.getPayload(), Map.class);

        // send message to all sessions
        // for (WebSocketSession webSocketSession : sessions) {
            // Map value = new Gson().fromJson(message.getPayload(), Map.class);
            // webSocketSession.sendMessage(new TextMessage(message.getPayload()));
        // }

        if(value.keySet().contains("subscribe")) {
            // start the service with the subscribe name
        } else if(value.keySet().contains("unsubscribe")) {
            // stop the service with the unsubscribe name or remove the session that unsubscribed
            // be careful not to stop the service if there are still sessions available
        } else {
            // do something with the sent object
            
            messagecount++;
            // create object myMessageNumberObject = {type: 'messageNumber', messagecount: messagecount}
            // session.sendMessage(new TextMessage(myMessageNumberObject))

            // emit message with type='message'
            session.sendMessage(new TextMessage(message.getPayload()));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // the messages will be broadcasted to all users.
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // do something on connection closed
    } 

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // handle binary message
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        // hanedle transport error
    }
}