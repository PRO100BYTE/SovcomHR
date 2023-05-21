package com.pro100byte.model

class SearchedVacancies(
    val totalNumber: Long,
    val pages: List<List<Long>>,
    val firstPage: List<Vacancy>
)
