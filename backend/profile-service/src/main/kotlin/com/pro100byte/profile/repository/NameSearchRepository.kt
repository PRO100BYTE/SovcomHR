package com.pro100byte.profile.repository

import com.pro100byte.profile.model.NameSearch
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface NameSearchRepository : JpaRepository<NameSearch, String> {
    @Query("SELECT ns from NameSearch ns WHERE ns.name = :name")
    fun findAllByName(@Param("name") name: String): List<NameSearch>
}
