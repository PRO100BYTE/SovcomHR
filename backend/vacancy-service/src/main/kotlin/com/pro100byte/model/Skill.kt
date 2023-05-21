package com.pro100byte.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "skill")
class Skill {
    @Id
    var skillTag: String? = null

    @OneToMany(mappedBy = "skill")
    var skillVacancies: List<VacancySkillTag>? = null
}
