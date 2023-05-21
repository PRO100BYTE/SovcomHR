package com.pro100byte.exception

class VacancyException(
    override val message: String?,
    val code: Int,
): RuntimeException()
