package bpos.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:App.xml")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {
        "bpos.server",
        "bpos.server.service",
        "bpos.server.controller",
})
public class BPositiveServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(BPositiveServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BPositiveServerApplication.class, args);
        logger.info("BPositiveServerApplication started successfully.");
    }
}
