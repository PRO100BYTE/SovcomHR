package com.pro100byte.vacancy.repository

import com.pro100byte.vacancy.model.VacancySkillTag
import com.pro100byte.vacancy.model.VacancySkillTagId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VacancySkillTagRepository: JpaRepository<VacancySkillTag, VacancySkillTagId>
