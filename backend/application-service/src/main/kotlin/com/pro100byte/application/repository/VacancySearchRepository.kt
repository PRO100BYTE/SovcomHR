package com.pro100byte.application.repository

import com.pro100byte.application.model.VacancySearch
import com.pro100byte.application.model.VacancySearchId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface VacancySearchRepository : JpaRepository<VacancySearch, VacancySearchId> {

    @Query("SELECT vs.vacancySearchId.applicationId FROM VacancySearch vs WHERE vs.vacancySearchId.vacancyId = :vacancyId")
    fun findAllByVacancyId(@Param("vacancyId") vacancyId: Long): List<Long>
}
