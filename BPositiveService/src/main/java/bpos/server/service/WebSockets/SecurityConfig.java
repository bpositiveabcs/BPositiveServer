package bpos.server.service.WebSockets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/client-websocket/**").permitAll() // Allow access to WebSocket endpoints
                                .anyRequest().permitAll() // Allow access to all other requests
                )
                .csrf().disable(); // Disable CSRF protection for WebSocket connections
        return http.build();
    }
}
