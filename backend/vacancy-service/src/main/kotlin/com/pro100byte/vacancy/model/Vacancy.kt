package com.pro100byte.vacancy.model

import com.pro100byte.location.model.Location
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "vacancy_location",
        joinColumns = [JoinColumn(name = "vacancy_id")],
        inverseJoinColumns = [JoinColumn(name = "location_id")]
    )
    var locations: List<Location>? = null

    @OneToMany(mappedBy = "vacancy", fetch = FetchType.LAZY)
    var vacancySkills: List<VacancySkillTag>? = null
}
