package com.pro100byte.model

import javax.persistence.*

@Entity
@Table(name = "SKILL_TAGS")
class Skill {
    @Id
    @Column(name = "tag")
    var tag: String? = null

    @OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
    var skillVacancies: List<VacancySkillTag>? = null
}
