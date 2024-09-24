package com.oct.L3.configurations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
    @Bean
    @ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF theo cú pháp mới
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // Các request yêu cầu xác thực khi security.enabled=true
                );
        return http.build();
    }
    @Bean
    @ConditionalOnProperty(name = "security.enabled", havingValue = "false")
    public SecurityFilterChain noSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Cho phép tất cả các request mà không cần xác thực khi security.enabled=false
                );
        return http.build();
    }
}