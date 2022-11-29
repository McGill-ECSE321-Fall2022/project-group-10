package ca.mcgill.ecse321.museum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ca.mcgill.ecse321.museum.service.JpaUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeRequests(auth -> {
                    auth.antMatchers("/").permitAll();
                    auth.antMatchers("/visitor").permitAll(); // Anyone can create a visitor account
                    // ... other rules
                })
            .userDetailsService(userDetailsService)
            .httpBasic(Customizer.withDefaults()) // Using HTTP Basic Authentication
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CredentialsEncoder();
    }

    // @Bean
    // public WebSecurity webSecurityCustomizer() { }
}