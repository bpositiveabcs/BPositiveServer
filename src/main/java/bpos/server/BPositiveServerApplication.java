package bpos.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
@ImportResource("classpath:App.xml")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {
        "bpos.server",
        "bpos.server.service"
})
public class BPositiveServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(BPositiveServerApplication.class);

    public static void main(String[] args) {
        try{
        ProcessBuilder processBuilder = new ProcessBuilder("python", "python/chatbot.py");
        processBuilder.directory(new java.io.File("C:\\Users\\bianc\\IdeaProjects\\Bpositive_nou\\BPositiveServer"));
        processBuilder.start();
        logger.info("Flask server started.");
    } catch (IOException e) {
        e.printStackTrace();
        logger.error("Failed to start Flask server: " + e.getMessage());
    }
        SpringApplication.run(BPositiveServerApplication.class, args);
        logger.info("BPositiveServerApplication started successfully.");
    }
}
