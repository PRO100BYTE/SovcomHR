package com.pro100byte.profile.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "PROFILES")
class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Access(AccessType.PROPERTY)
    var id: Long? = null

    @Column(name = "firstName", nullable = true)
    var firstName: String? = null

    @Column(name = "lastName", nullable = true)
    var lastName: String? = null

    @Column(name = "patronymic", nullable = true)
    var patronymic: String? = null

    @Column(name = "birthDate", nullable = true)
    var birthDate: Long? = null

    @Column(name = "avatar", nullable = true)
    var avatar: String? = null

    @Column(name = "cv", nullable = true)
    var cv: String? = null

    @Column(name = "video", nullable = true)
    var video: String? = null

    @Column(name = "location", nullable = true)
    var location: String? = null

    @Column(name = "verified")
    var verified: Boolean = false

    @Column(name = "enabled")
    var enabled: Boolean = false

    @Column(name = "passportSerial")
    var passportSerial: String? = null

    @Column(name = "passportNumber")
    var passportNumber: String? = null

    @Column(name = "inn")
    var inn: String? = null

    @ManyToMany(mappedBy = "profiles")
    var skillTags: List<SkillTag>? = null
}
