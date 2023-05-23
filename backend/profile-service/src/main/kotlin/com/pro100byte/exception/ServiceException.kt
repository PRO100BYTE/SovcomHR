package com.pro100byte.exception

class ServiceException(
    override val message: String?,
    val code: Int,
): RuntimeException() {
    companion object {
        fun smthWentWrong(): ServiceException = ServiceException("Smth went wrong", 500)
    }
}
