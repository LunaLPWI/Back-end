package com.luna.luna_project.configurations.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // se precisar enviar cookies ou auth info
        config.addAllowedOriginPattern("*"); // permite todas as origens
        config.addAllowedHeader("*");        // permite todos os headers
        config.addAllowedMethod("*");        // permite todos os métodos HTTP (GET, POST, etc)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
