package com.pro100byte.profile.repository

import com.pro100byte.profile.model.SkillTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SkillTagRepository : JpaRepository<SkillTag, Long>
