package com.pro100byte.repository

import com.pro100byte.model.VacancySkillTag
import com.pro100byte.model.VacancySkillTagId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VacancySkillTagRepository: JpaRepository<VacancySkillTag, VacancySkillTagId>
