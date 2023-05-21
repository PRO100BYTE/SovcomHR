package com.pro100byte.model

import javax.persistence.*

@Entity
@Table(
    name = "VACANCY_SKILL_TAG",
    indexes = [Index(name = "index_skill_tag", columnList = "skill_tag")]
)
class VacancySkillTag {
    @EmbeddedId
    @AttributeOverride(
        name = "skillTag",
        column = Column(name = "skill_tag")
    )
    @AttributeOverride(
        name = "vacancyId",
        column = Column(name = "vacancy_id")
    )
    var id: VacancySkillTagId? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vacancy_id")
    var vacancy: OpenVacancy? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skill_tag")
    var skill: Skill? = null
}
