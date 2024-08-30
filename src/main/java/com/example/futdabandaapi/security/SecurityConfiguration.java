package com.example.futdabandaapi.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration implements WebMvcConfigurer {

    private final SecurityFilter securityFilter;

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/users/token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/player").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/role").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/players").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/user").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/display/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/ban").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/unban").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/clubs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clubs/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clubs/display/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clubs").hasAnyAuthority("ADMIN", "PLAYER")
                        .requestMatchers(HttpMethod.POST, "/championships").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/championships").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/championships").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/championships").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/championships/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/championships/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/games").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/games").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/games").permitAll()
                        .requestMatchers(HttpMethod.GET, "/games/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/fields").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/fields").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/fields").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/fields").permitAll()
                        .requestMatchers(HttpMethod.GET, "/fields/{id}").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200", "https://futdabanda.netlify.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}
