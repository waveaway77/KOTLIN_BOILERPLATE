package com.example.demo.controller

import com.example.demo.dto.WeatherRequest
import com.example.demo.service.WeatherService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/*
https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m
 */

@RestController
@RequestMapping("/weather")
class WeatherController(
    private val weatherService: WeatherService
) {
    @PostMapping("/forecast")
    suspend fun getWheather(
        @RequestBody request: WeatherRequest
    ) {
        weatherService.getWeather(request)
    }
}