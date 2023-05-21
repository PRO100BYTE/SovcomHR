package com.pro100byte.vacancy.model

class VacancyCreation(
    val title: String,
    val body: String,
    val skillTags: List<String>,
    val locations: List<Long>,
)
