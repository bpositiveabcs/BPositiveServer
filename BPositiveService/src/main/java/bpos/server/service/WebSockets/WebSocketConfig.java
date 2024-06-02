package bpos.server.service.WebSockets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ClientWebSocketHandler clientWebSocketHandler;
    private final CustomHandshakeInterceptor customHandshakeInterceptor;

    @Autowired
    public WebSocketConfig(ClientWebSocketHandler clientWebSocketHandler, CustomHandshakeInterceptor customHandshakeInterceptor) {
        this.clientWebSocketHandler = clientWebSocketHandler;
        this.customHandshakeInterceptor = customHandshakeInterceptor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/client-websocket")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Bean(name = "customSimpMessagingTemplate")
    public SimpMessagingTemplate simpMessagingTemplate() {
        return new SimpMessagingTemplate(clientOutboundChannel());
    }

    @Bean
    public ExecutorSubscribableChannel clientInboundChannel() {
        return new ExecutorSubscribableChannel();
    }

    @Bean
    public ExecutorSubscribableChannel clientOutboundChannel() {
        return new ExecutorSubscribableChannel();
    }
}
