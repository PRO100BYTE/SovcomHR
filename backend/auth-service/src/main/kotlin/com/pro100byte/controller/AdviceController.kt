package com.pro100byte.controller

import com.pro100byte.dto.Error
import com.pro100byte.exception.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AdviceController {
    @ExceptionHandler(ServiceException::class)
    fun handleAuthException(exception: ServiceException): ResponseEntity<Error> {
        return ResponseEntity(Error().apply {
            this.errorMessage = exception.message
            this.stacktrace = exception.stackTrace.map {
                it.toString()
            }
        }, HttpStatus.valueOf(exception.code))
    }
}