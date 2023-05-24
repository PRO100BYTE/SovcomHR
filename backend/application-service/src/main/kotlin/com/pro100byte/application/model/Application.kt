package com.pro100byte.application.model

import javax.persistence.*

@Entity
@Table(name = "applications")
class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Access(AccessType.PROPERTY)
    var id: Long? = null

    @Column(name = "vacancy_id")
    var vacancyId: Long? = null

    @Column(name = "profile_id")
    var profileId: Long? = null

    @Column(name = "status")
    @Enumerated
    var status: ApplicationStatus = ApplicationStatus.NEW

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "application")
    var profileSearch: ProfileSearch? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "application")
    var vacancySearch: VacancySearch? = null
}
