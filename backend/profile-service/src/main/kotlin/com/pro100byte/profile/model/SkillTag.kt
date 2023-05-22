package com.pro100byte.profile.model

import javax.persistence.*

@Entity
@Table(name = "skill_tags")
class SkillTag {
    @Id
    var skillTag: String? = null

    @ManyToMany
    @JoinTable(
        name = "skill_tag_profile",
        joinColumns = [JoinColumn(name = "vacancy_id")],
        inverseJoinColumns = [JoinColumn(name = "skill_tag_id")]
    )
    var profiles: List<Profile>? = null
}