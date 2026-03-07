package com.example.demo.service

import com.example.demo.client.ApiClient
import com.example.demo.dto.WeatherRequest
import com.example.demo.dto.WeatherResponse
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service

@Service
class WeatherService(
    val validationService: ValidationService,
    val apiClient: ApiClient
) {
    private val log = LogManager.getLogger()

    suspend fun getWeather(request: WeatherRequest): WeatherResponse {
        println("MDC = ${org.slf4j.MDC.getCopyOfContextMap()}") // MDC = {traceId=69915fe0eee8350ea1cab5b94bc29279, spanId=59c33a4b82c71934}

        val validRequest = validationService.validateCheck(request)
        return apiClient.get(
            baseUrl = "https://api.open-meteo.com/v1",
            endpoint = "/forecast",
            requestParams = emptyMap(),
            headers = emptyMap(),
            responseType = WeatherResponse::class.java,
        )
    }
}