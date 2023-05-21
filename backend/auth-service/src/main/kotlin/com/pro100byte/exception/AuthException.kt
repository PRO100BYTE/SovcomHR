package com.pro100byte.exception

class AuthException(
    override val message: String,
    val code: Int,
): RuntimeException()
