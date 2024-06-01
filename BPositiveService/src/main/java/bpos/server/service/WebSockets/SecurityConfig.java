//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/client-websocket/**").permitAll() // Allow access to WebSocket endpoints
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable() // Disable CSRF protection for WebSocket connections
//                .headers().frameOptions().sameOrigin(); // Allow same origin for frame options
//    }
//}
