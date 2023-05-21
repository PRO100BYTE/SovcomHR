package com.pro100byte.controller

import com.pro100byte.api.AuthApi
import com.pro100byte.dto.LoginDataView
import com.pro100byte.dto.LoginResultView
import com.pro100byte.dto.RegisterDataView
import com.pro100byte.dto.RegisterResultView
import com.pro100byte.model.LoginData
import com.pro100byte.model.RegisterData
import com.pro100byte.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) : AuthApi{
    override fun login(loginDataView: LoginDataView): ResponseEntity<LoginResultView> {
        val loginResult = authService.login(LoginData(
            loginDataView.email,
            loginDataView.password
        ))

        return ResponseEntity.ok(LoginResultView().apply {
            this.accessToken = loginResult.accessToken
            this.expireIn = loginResult.expireIn.toBigDecimal()
            this.userType = loginResult.roles
        })
    }

    override fun registerUser(registerDataView: RegisterDataView): ResponseEntity<RegisterResultView> {
        val registerResult = authService.registerUser(RegisterData(
            registerDataView.email,
            registerDataView.password
        ))

        return ResponseEntity.ok(RegisterResultView().apply {
            this.accessToken = registerResult.accessToken
            this.expireIn = registerResult.expireIn.toBigDecimal()
            this.userType = registerResult.roles
        })
    }

    override fun registerHR(registerDataView: RegisterDataView): ResponseEntity<RegisterResultView> {
        val registerResult = authService.registerHR(RegisterData(
            registerDataView.email,
            registerDataView.password
        ))

        return ResponseEntity.ok(RegisterResultView().apply {
            this.accessToken = registerResult.accessToken
            this.expireIn = registerResult.expireIn.toBigDecimal()
            this.userType = registerResult.roles
        })
    }
}
