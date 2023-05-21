package com.pro100byte.location.model

import com.pro100byte.vacancy.model.OpenVacancy
import javax.persistence.*

@Entity
@Table(name = "LOCATION")
class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null

    @Column(name = "city")
    var city: String? = null
    @Column(name = "street")
    var street: String? = null
    @Column(name = "number")
    var number: String? = null

    @ManyToMany(fetch = FetchType.LAZY)
    var vacancies: List<OpenVacancy>? = null
}
