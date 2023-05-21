package com.pro100byte.model

import javax.persistence.*

@Entity
@Table(
    name = "VACANCY_SKILL_TAG",
    indexes = [ Index(columnList = "skill_tag") ]
)
class VacancySkillTag {
    @EmbeddedId
    var id: VacancySkillTagId? = null

    @ManyToOne
    @MapsId("vacancy_id")
    var vacancy: OpenVacancy? = null

    @ManyToOne
    @MapsId("skill_tag")
    var skill: Skill? = null
}
