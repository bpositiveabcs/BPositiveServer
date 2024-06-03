package bpos.server.service.WebSockets;

import bpos.common.model.Center;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ClientWebSocketHandler clientWebSocketHandler;
    private final CustomHandshakeInterceptor customHandshakeInterceptor;
    private final CenterWebSocketHandler centerWebSocketHandler;
    private final AdminWebSocketHandler adminWebSocketHandler;

    @Autowired
    public WebSocketConfig(ClientWebSocketHandler clientWebSocketHandler, AdminWebSocketHandler adminWebSocketHandler,
                           CenterWebSocketHandler centerWebSocketHandler,CustomHandshakeInterceptor customHandshakeInterceptor) {
        this.clientWebSocketHandler = clientWebSocketHandler;
        this.centerWebSocketHandler = centerWebSocketHandler;
        this.adminWebSocketHandler = adminWebSocketHandler;
        this.customHandshakeInterceptor = customHandshakeInterceptor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/client-websocket").addInterceptors(customHandshakeInterceptor)
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/center-websocket").addInterceptors(customHandshakeInterceptor)
                .setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/admin-websocket").addInterceptors(customHandshakeInterceptor)
                .setAllowedOriginPatterns("*").withSockJS();
    }



}
