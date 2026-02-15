package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.firewall.DefaultHttpFirewall
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(
        http: ServerHttpSecurity
    ): SecurityWebFilterChain? {
        val httpSecurity = http
            .authorizeExchange {
                it.pathMatchers("/**").permitAll()
            }
            .cors { corsConfiguration() } // If you’re using a frontend SPA, enabling CORS is essential: Due to Single-origin
            .sessionManagement { SessionCreationPolicy.STATELESS } // If you use JWT or Basic Authentication, you often set the session to stateless, meaning no session token is managed:
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }

        return httpSecurity.build()
    }

    @Bean
    fun configure(): WebSecurityCustomizer = WebSecurityCustomizer {
        it
            .httpFirewall(DefaultHttpFirewall())
            .ignoring().requestMatchers(
                "/**"
            )
    }

    fun corsConfiguration(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.exposedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}