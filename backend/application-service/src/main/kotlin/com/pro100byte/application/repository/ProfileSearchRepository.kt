package com.pro100byte.application.repository

import com.pro100byte.application.model.ProfileSearch
import com.pro100byte.application.model.ProfileSearchId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProfileSearchRepository : JpaRepository<ProfileSearch, ProfileSearchId> {

    @Query("SELECT ps.profileSearchId.applicationId from ProfileSearch ps WHERE ps.profileSearchId.profileId = :profileId")
    fun findAllByProfileId(@Param("profileId") profileId: Long): List<Long>
}
