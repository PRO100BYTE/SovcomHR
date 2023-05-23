package com.pro100byte.vacancy.model

import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable

@Embeddable
class VacancySkillTagId : Serializable {
    var vacancyId: Long? = null
    var skillTag: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true

        if (other == null)
            return false

        if (javaClass != other::class.java)
            return false

        return (other as VacancySkillTagId).run {
            Objects.equals(this.vacancyId, other.vacancyId) && Objects.equals(
                this.skillTag,
                this.skillTag
            )
        }
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = (prime * result
                + if (vacancyId == null) 0 else vacancyId.hashCode())
        result = (prime * result
                + if (skillTag == null) 0 else skillTag.hashCode())
        return result
    }
}