package com.pro100byte.vacancy.repository

import com.pro100byte.vacancy.model.OpenVacancy
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OpenVacancyRepository: JpaRepository<OpenVacancy, Long> {

    @Query("SELECT ov.id FROM OpenVacancy ov")
    fun find1000NextVacancies(pageable: Pageable): List<Long>
}
