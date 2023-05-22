package com.pro100byte.profile.repository

import com.pro100byte.profile.model.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<Profile, Long>
