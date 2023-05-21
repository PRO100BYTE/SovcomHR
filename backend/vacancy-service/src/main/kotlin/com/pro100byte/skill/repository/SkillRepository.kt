package com.pro100byte.skill.repository

import com.pro100byte.skill.model.Skill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SkillRepository: JpaRepository<Skill, String>
