package com.pro100byte.profile.model

import java.math.BigDecimal

class ProfileEdit(
    val id: Long,
    val firstName: String? = null,
    val lastName: String? = null,
    val patronymic: String? = null,
    val birthDate: BigDecimal? = null,
    val avatar: String? = null,
    val skillTags: List<String>? = null,
    val location: String? = null,
    val cv: String? = null,
)
