package com.pro100byte.vacancy.model

class SearchedVacancies(
    val totalNumber: Long,
    val pages: List<List<Long>>,
    val firstPage: List<Vacancy>
)
