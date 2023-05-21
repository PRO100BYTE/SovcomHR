package com.pro100byte.repository

import com.pro100byte.model.OpenVacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OpenVacancyRepository: JpaRepository<OpenVacancy, Long>
