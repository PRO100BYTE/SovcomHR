package com.pro100byte.config

import com.pro100byte.model.RegisterData
import com.pro100byte.service.AuthService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class InitConfig(
    private val authService: AuthService,
) {

    @EventListener(ApplicationReadyEvent::class)
    fun onStartup() {
        val userUserData = RegisterData("user@mail.ru", "user")
        val hrUserData = RegisterData("hr@mail.ru", "hr")
        val adminUserData = RegisterData("admin@mail.ru", "admin")

        authService.registerUser(userUserData)
        authService.registerHR(hrUserData)
        authService.registerAdmin(adminUserData)
    }
}