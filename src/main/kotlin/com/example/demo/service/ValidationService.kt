package com.example.demo.service

import org.springframework.stereotype.Service

@Service
class ValidationService {
    fun <T> validateCheck(request: T): T {
        return request
    }
}