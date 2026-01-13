package com.example.demo.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@Configuration
class WebClientConfig() {

    @Bean
    fun webClient(): WebClient {
        val provider = ConnectionProvider.builder("custom-provider")
            .maxConnections(10)
            .maxIdleTime(Duration.ofMinutes(10))
            .maxLifeTime(Duration.ofMinutes(10))
            .pendingAcquireTimeout(Duration.ofMinutes(5))
            .pendingAcquireMaxCount(-1)
            .evictInBackground(Duration.ofMinutes(5))
            .lifo()
            .metrics(true)
            .build()

        val webClient = WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create(provider)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000) // TCP handshake
                        .doOnConnected {
                            it.addHandlerLast(WriteTimeoutHandler(240))
                            it.addHandlerLast(ReadTimeoutHandler(240))
                        }.responseTimeout(Duration.ofSeconds(125))
                )
            )
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        return webClient
    }
}