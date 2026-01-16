package com.example.demo.controller

import com.example.demo.dto.WeatherRequest
import com.example.demo.service.WeatherService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

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
        println("==== controller called ====")

        val log2 = LoggerFactory.getLogger("TEST")
        log2.info("SLF4J TEST LOG")

        val log = KotlinLogging.logger {}
        log.info { "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" }
        weatherService.getWeather(request)
    }
}