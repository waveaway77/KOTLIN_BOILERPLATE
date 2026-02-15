package com.example.demo.dto

import com.example.demo.util.MaskedName
import org.jetbrains.annotations.NotNull

data class WeatherRequest(
    @NotNull
    val name: MaskedName,

    @NotNull
    val status: Status
)

enum class Status {
    READY,
    DONE,
    FAIL
}