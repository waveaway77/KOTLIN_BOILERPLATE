package com.example.demo.advice

import com.example.demo.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.InvalidMediaTypeException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime




/*
애플리케이션 전체에서 사용할 어드바이저로 등록합니다.
이러한 어드바이저 클래스는 내부 컴포넌트인 `resolver`에 의해 관리됩니다.
ExceptionHandlerExceptionResolver이 `resolver`는 컨트롤러에서 예외가 발생했을 때 어떤 처리를 해야 하는지 결정하는 역할을 합니다.
 */
@RestControllerAdvice
class RestControllerAdvice {

    @ExceptionHandler(Exception::class)
    suspend fun handleCustomError(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(
                ex.message.toString(),
                LocalDateTime.now().toString()
            )
        )
    }


//    @ExceptionHandler(NullPointerException::class)
//    fun handleNull(ex: NullPointerException?): ResponseEntity<String> {
//        return ResponseEntity
//            .status(500)
//            .body("Null value encountered")
//    }
//
//    @ExceptionHandler(RuntimeException::class)
//    fun handleRuntime(ex: RuntimeException?): ResponseEntity<String> {
//        return ResponseEntity
//            .status(HttpStatus.INTERNAL_SERVER_ERROR)
//            .body("Something went wrong")
//    }
}