package com.pro100byte.application.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(
    name = "vacancy_search",
    indexes = [Index(name = "vacancy_index", columnList = "vacancy_id")]
)
class VacancySearch {
    @EmbeddedId
    @AttributeOverrides(
        AttributeOverride(
            name = "vacancyId",
            column = Column(name = "vacancy_id")
        ),
        AttributeOverride(
            name = "applicationId",
            column = Column(name = "application_id")
        )
    )
    var vacancySearchId: VacancySearchId? = null

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("applicationId")
    @JoinColumn(name = "application_id")
    var application: Application? = null
}

@Embeddable
class VacancySearchId : Serializable {
    var vacancyId: Long? = null
    var applicationId: Long? = null
}
