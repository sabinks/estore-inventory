package com.estore.config;

import com.estore.filter.JWTTokenGeneratorFilter;
import com.estore.filter.JWTTokenValidatorFilter;
import com.estore.filter.UserActiveFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity(debug = false)

public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{
        http.csrf().disable();
        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("*"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600L);
                return config;
            }
        });
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(
                        "/api/register",
                        "api/kafka/**",
                        "api/send-mail/**",
                        "api/email/**",
                        "api/forgot-password",
                        "api/reset-password"
                ).permitAll()
                .anyRequest().authenticated()

        );

        http.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);
        http.addFilterAfter(new UserActiveFilter(), BasicAuthenticationFilter.class);
//
//        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
