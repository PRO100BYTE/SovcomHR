package com.pro100byte.profile.model

import javax.persistence.*

@Entity
@Table(
    name = "name_search"
)
class NameSearch {
    @Id
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    var profile: Profile? = null
}
