package com.pro100byte.service

import com.pro100byte.exception.AuthException
import com.pro100byte.model.*
import com.pro100byte.repository.UserRepository
import com.pro100byte.util.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${jwt.expireIn}") val expireIn: Long,
) {
    fun login(loginData: LoginData): LoginResult {
        val user = userRepository.findById(loginData.email).orElseThrow {
            throw AuthException("User with email %s was not found".format(loginData.email), 401)
        }

        if (!passwordEncoder.matches(loginData.password, user.password)) {
            throw AuthException("Wrong password!", 401)
        }

        return LoginResult(
            jwtUtil.createJwt(user),
            expireIn,
            user.roles ?: throw AuthException("Smth went wrong during user creation", 500)
        )
    }

    fun registerUser(registerUserData: RegisterData): RegisterResult {
        if (userRepository.existsById(registerUserData.email)) {
            throw AuthException(
                "User with such email: %s already exists".format(registerUserData.email),
                400
            )
        }

        val roles = listOf(Role.USER.name).joinToString(separator = ",")
        val newUser = User().apply {
            this.email = registerUserData.email
            this.password = passwordEncoder.encode(registerUserData.password)
            this.roles = roles
        }

        userRepository.save(newUser)

        return RegisterResult(
            jwtUtil.createJwt(newUser),
            expireIn,
            roles
        )
    }

    fun registerHR(registerUserData: RegisterData): RegisterResult {
        if (userRepository.existsById(registerUserData.email)) {
            throw AuthException(
                "User with such email: %s already exists".format(registerUserData.email),
                400
            )
        }

        val roles = listOf(Role.USER.name, Role.HR.name).joinToString(separator = ",")
        val newUser = User().apply {
            this.email = registerUserData.email
            this.password = passwordEncoder.encode(registerUserData.password)
            this.roles = roles
        }

        userRepository.save(newUser)

        return RegisterResult(
            jwtUtil.createJwt(newUser),
            expireIn,
            roles
        )
    }

    fun registerAdmin(registerUserData: RegisterData): RegisterResult {
        if (userRepository.existsById(registerUserData.email)) {
            throw AuthException(
                "User with such email: %s already exists".format(registerUserData.email),
                400
            )
        }

        val roles = listOf(Role.USER.name, Role.HR.name, Role.ADMIN.name).joinToString(separator = ",")
        val newUser = User().apply {
            this.email = registerUserData.email
            this.password = passwordEncoder.encode(registerUserData.password)
            this.roles = roles
        }

        userRepository.save(newUser)

        return RegisterResult(
            jwtUtil.createJwt(newUser),
            expireIn,
            roles
        )
    }
}
