package bpos.server.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@ImportResource("classpath:App.xml")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
//@RestController
public class BPositiveControllerApplication {


    public static void main(String[] args) {
        SpringApplication.run(BPositiveControllerApplication.class, args);

    }


}
