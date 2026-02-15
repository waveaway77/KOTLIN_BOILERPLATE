package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
//import org.springframework.web.reactive.config.EnableWebFlux

@ConfigurationPropertiesScan
@SpringBootApplication
class Demo1Application

fun main(args: Array<String>) {
    runApplication<Demo1Application>(*args)
}
