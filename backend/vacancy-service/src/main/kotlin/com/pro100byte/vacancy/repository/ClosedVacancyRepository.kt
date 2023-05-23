package com.pro100byte.vacancy.repository

import com.pro100byte.vacancy.model.ClosedVacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClosedVacancyRepository : JpaRepository<ClosedVacancy, Long>
