package com.example.demo.client

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class ApiClient(
    private val webClient: WebClient
) {
    suspend fun <T: Any> get(
        baseUrl: String,
        endpoint: String,
        requestParams: Map<String, Any> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        responseType: Class<T>,
    ): T {
        val response = webClient.get()
            .uri(baseUrl+endpoint)
            .accept(MediaType.APPLICATION_JSON)
            .headers { headers }
            .retrieve()
            .bodyToMono(responseType)
            .awaitSingle()

        return response
    }
}