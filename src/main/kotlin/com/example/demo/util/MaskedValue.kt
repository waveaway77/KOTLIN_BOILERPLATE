package com.example.demo.util

@JvmInline
value class MaskedName(private val value: String) {

    fun raw(): String = value

    override fun toString(): String = maskMiddle(value)
}

fun maskMiddle(value: String, maskChar: Char = '*'): String {
    if (value.length <= 2) return value.first() + maskChar.toString()

    val mask = maskChar.toString().repeat(value.length - 2)
    return value.first() + mask + value.last()
}
