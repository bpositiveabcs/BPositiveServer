package bpos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication(scanBasePackages = "bpos.server")
@ImportResource("classpath:App.xml")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BPositiveServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BPositiveServerApplication.class, args);
    }

}
