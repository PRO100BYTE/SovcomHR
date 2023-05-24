package com.pro100byte.application.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(
    name = "profile_search",
    indexes = [Index(name = "profile_index", columnList = "profile_id")]
)
class ProfileSearch {
    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(
            name = "profileId",
            column = Column(name = "profile_id")
        ),
        AttributeOverride(
            name = "applicationId",
            column = Column(name = "application_id")
        )
    )
    var profileSearchId: ProfileSearchId? = null

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("applicationId")
    @JoinColumn(name = "application_id")
    var application: Application? = null
}

@Embeddable
class ProfileSearchId : Serializable {
    var profileId: Long? = null
    var applicationId: Long? = null
}
