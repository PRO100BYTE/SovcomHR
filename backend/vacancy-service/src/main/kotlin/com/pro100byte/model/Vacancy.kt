package com.pro100byte.model

import javax.persistence.*

@MappedSuperclass
abstract class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null

    @Column(name = "title")
    var title: String? = null
    @Column(name = "body")
    var body: String? = null
    @Column(name = "date")
    var date: Long? = null

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.LAZY)
    var vacancySkills: List<VacancySkillTag>? = null
}
