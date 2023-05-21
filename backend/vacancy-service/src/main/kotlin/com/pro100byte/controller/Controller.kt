package com.pro100byte.controller

import com.pro100byte.api.VacancyApi
import com.pro100byte.dto.SearchedVacanciesView
import com.pro100byte.dto.VacancyCreationView
import com.pro100byte.dto.VacancyMetadataView
import com.pro100byte.dto.VacancyView
import com.pro100byte.exception.VacancyException
import com.pro100byte.model.VacancyCreation
import com.pro100byte.service.VacancyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class Controller(
    private val vacancyService: VacancyService,
): VacancyApi {
    override fun filteredVacancies(skillTags: MutableList<String>): ResponseEntity<MutableList<SearchedVacanciesView>> {
        return super.filteredVacancies(skillTags)
    }

    override fun getVacancy(id: BigDecimal): ResponseEntity<VacancyView> {
        val vacancy = vacancyService.getVacancy(id.toLong())
        return ResponseEntity.ok(
            VacancyView().apply {
                this.id = vacancy.id?.toBigDecimal()
                    ?: throw VacancyException("Error during finding vacancy with id: $id", 500)
                this.body = vacancy.body
                this.date = vacancy.date?.toBigDecimal()
                    ?: throw VacancyException("Error during finding vacancy with id: $id", 500)
                this.title = vacancy.title
                this.skillTags = vacancy.vacancySkills?.map { it.skill?.skillTag }
                    ?: throw VacancyException("Error during finding vacancy with id: $id", 500)
            }
        )
    }

    override fun topVacancies(): ResponseEntity<MutableList<VacancyView>> {
        return super.topVacancies()
    }

    override fun vacancyCreate(vacancyCreationView: VacancyCreationView): ResponseEntity<VacancyMetadataView> {
        if (vacancyCreationView.title == null || vacancyCreationView.body == null || vacancyCreationView.skillTags == null) {
            throw VacancyException("Wrong request!", 400)
        }

        val vacancyMeta = vacancyService.createVacancy(VacancyCreation(
            vacancyCreationView.title,
            vacancyCreationView.body,
            vacancyCreationView.skillTags
        ))

        return ResponseEntity.ok(
            VacancyMetadataView().apply {
                this.id = vacancyMeta.id.toBigDecimal()
            }
        )
    }
}