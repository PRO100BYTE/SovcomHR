package com.pro100byte.profile.model

import javax.persistence.*

@Entity
@Table(name = "skill_tags")
class SkillTag {
    @Id
    @Column(name = "skill_tag_id")
    var skillTag: String? = null

    @ManyToMany(mappedBy = "skillTags")
    var profiles: MutableList<Profile>? = null
}
