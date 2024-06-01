package bpos.server.service.WebSockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        logger.info("Configuring message broker...");
        config.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[]{10000, 10000})
                .setTaskScheduler(heartBeatScheduler());
        config.setApplicationDestinationPrefixes("/app");
        logger.info("Message broker configured.");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        logger.info("Registering STOMP endpoints...");
        registry.addEndpoint("/client-websocket").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/center-websocket").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/admin-websocket").setAllowedOriginPatterns("*").withSockJS();
        logger.info("STOMP endpoints registered.");
    }

    @Bean
    public SimpMessagingTemplate messagingTemplate(@Qualifier("brokerChannel") MessageChannel messageChannel) {
        return new SimpMessagingTemplate(messageChannel);
    }

    @Bean
    public TaskScheduler heartBeatScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("wss-heartbeat-thread-");
        taskScheduler.initialize();
        return taskScheduler;
    }
}
