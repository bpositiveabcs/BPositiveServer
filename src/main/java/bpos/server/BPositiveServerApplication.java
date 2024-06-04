package bpos.server;

import bpos.server.service.WebSockets.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.io.IOException;

@SpringBootApplication
@ImportResource("classpath:App.xml")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {
        "bpos.server",
        "bpos.server.service",
        "bpos.server.service.WebSockets"
})
@EnableScheduling
@EnableWebSocketMessageBroker
@EnableWebSocket
public class BPositiveServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(BPositiveServerApplication.class);

    public static void main(String[] args) {
        try{
        ProcessBuilder processBuilder = new ProcessBuilder("python", "python/chatbot.py");
        processBuilder.start();
        logger.info("Flask server started.");
    } catch (IOException e) {
        e.printStackTrace();
        logger.error("Failed to start Flask server: " + e.getMessage());
    }

        SpringApplication.run(BPositiveServerApplication.class, args);
//        logger.info("BPositiveServerApplication started successfully.");
//    }
    }
}
