package com.pro100byte.repository

import com.pro100byte.model.Skill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SkillRepository: JpaRepository<Skill, String>
