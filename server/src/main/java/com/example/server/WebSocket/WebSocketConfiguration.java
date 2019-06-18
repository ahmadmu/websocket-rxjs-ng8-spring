package com.example.server.websocket;

import java.util.Map;

import com.example.server.websocket.SocketHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/")
                .setAllowedOrigins("*")
                // initial Request/Handshake interceptor
                .addInterceptors(new HttpSessionHandshakeInterceptor() {

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception ex) {
                        super.afterHandshake(request, response, wsHandler, ex);
                    }

                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, 
                                                    ServerHttpResponse response, WebSocketHandler wsHandler, 
                                                    Map<String, Object> attributes) throws Exception {
                        boolean b = super.beforeHandshake(request, response, wsHandler, attributes);
                                    // && (request.getPrincipal()).isAuthenticated();
                        return b;
                    }
 
                });
                // for IE8, 9 support 
                // https://docs.spring.io/spring-framework/docs/5.0.0.M1/spring-framework-reference/html/websocket.html#websocket-fallback-xhr-vs-iframe
                // .withSockJS();
        
    }

    // @Bean
    // public ServletServerContainerFactoryBean createWebSocketContainer() {
    //     ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    //     container.setMaxTextMessageBufferSize(8192);
    //     container.setMaxBinaryMessageBufferSize(8192);
    //     return container;
    // }

}