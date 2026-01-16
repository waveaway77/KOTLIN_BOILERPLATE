package com.example.demo.service

import com.example.demo.client.ApiClient
import com.example.demo.dto.WeatherRequest
import com.example.demo.dto.WeatherResponse
import org.springframework.stereotype.Service

@Service
class WeatherService(
    val validationService: ValidationService,
    val apiClient: ApiClient
) {
    suspend fun getWeather(request: WeatherRequest): String {
        val validRequest = validationService.validateCheck(request)
        return apiClient.get(
            baseUrl = "https://api.open-meteo.com/v1",
            endpoint = "/forecast",
            requestParams = emptyMap(),
            headers = emptyMap(),
            responseType = String::class.java,
        )
    }
}