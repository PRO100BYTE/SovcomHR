package com.pro100byte.vacancy.controller

import com.pro100byte.api.VacancyApi
import com.pro100byte.dto.SearchedVacanciesView
import com.pro100byte.dto.VacancyCreationView
import com.pro100byte.dto.VacancyMetadataView
import com.pro100byte.dto.VacancyView
import com.pro100byte.exception.ServiceException
import com.pro100byte.vacancy.model.VacancyCreation
import com.pro100byte.vacancy.model.VacancyFilter
import com.pro100byte.vacancy.service.VacancyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class VacancyController(
    private val vacancyService: VacancyService,
): VacancyApi {
    override fun filteredVacancies(
        skillTags: MutableList<String>?,
        locationTags: MutableList<BigDecimal>?
    ): ResponseEntity<SearchedVacanciesView>? {
        val searchedVacancies = vacancyService.filteredVacancies(
            VacancyFilter(
                skillTags = skillTags,
                locationTags = locationTags?.map { it.toLong() }
            )
        )

        return ResponseEntity.ok(
            SearchedVacanciesView().apply {
                this.totalNumber = searchedVacancies.totalNumber.toBigDecimal()
                this.pages = searchedVacancies.pages.map { it.map { it.toBigDecimal() } }
                this.firstPage = searchedVacancies.firstPage.map {
                    VacancyView().apply {
                        this.id = it.id?.toBigDecimal()
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.title = it.title
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.skillTags = it.vacancySkills?.map { it.skill?.tag }
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.locationTags = it.locations?.map { it.id?.toBigDecimal() }
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.body = it.body
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.date = it.date?.toBigDecimal()
                            ?: throw ServiceException("Error during finding vacancies", 500)
                    }
                }
            }
        )
    }

    override fun getVacancy(id: BigDecimal): ResponseEntity<VacancyView> {
        val vacancy = vacancyService.getVacancy(id.toLong())
        return ResponseEntity.ok(
            VacancyView().apply {
                this.id = vacancy.id?.toBigDecimal()
                    ?: throw ServiceException("Error during finding vacancy with id: $id", 500)
                this.body = vacancy.body
                    ?: throw ServiceException("Error during finding vacancy with id: $id", 500)
                this.date = vacancy.date?.toBigDecimal()
                    ?: throw ServiceException("Error during finding vacancy with id: $id", 500)
                this.title = vacancy.title
                    ?: throw ServiceException("Error during finding vacancy with id: $id", 500)
                this.skillTags = vacancy.vacancySkills?.map { it.skill?.tag }
                    ?: throw ServiceException("Error during finding vacancy with id: $id", 500)
                this.locationTags = vacancy.locations?.map { it.id?.toBigDecimal() }
                    ?: throw ServiceException("Error during finding vacancy with id: $id", 500)
            }
        )
    }

    override fun topVacancies(): ResponseEntity<SearchedVacanciesView>? {
        val searchedVacancies = vacancyService.topVacancies()

        return ResponseEntity.ok(
            SearchedVacanciesView().apply {
                this.totalNumber = searchedVacancies.totalNumber.toBigDecimal()
                this.pages = searchedVacancies.pages.map { it.map { it.toBigDecimal() } }
                this.firstPage = searchedVacancies.firstPage.map {
                    VacancyView().apply {
                        this.id = it.id?.toBigDecimal()
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.title = it.title
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.skillTags = it.vacancySkills?.map { it.skill?.tag }
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.locationTags = it.locations?.map { it.id?.toBigDecimal() }
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.body = it.body
                            ?: throw ServiceException("Error during finding vacancies", 500)
                        this.date = it.date?.toBigDecimal()
                            ?: throw ServiceException("Error during finding vacancies", 500)
                    }
                }
            }
        )
    }

    override fun getVacanciesByIds(ids: MutableList<BigDecimal>): ResponseEntity<MutableList<VacancyView>> {
        return ResponseEntity.ok(
            vacancyService
                .getVacanciesByIds(ids.map { it.toLong() })
                .map {
                    VacancyView().apply {
                        this.id = it.id?.toBigDecimal()
                            ?: throw ServiceException("Error during finding vacancy with id: ${it.id}", 500)
                        this.title = it.title
                            ?: throw ServiceException("Error during finding vacancy with id: ${it.id}", 500)
                        this.date = it.date?.toBigDecimal()
                            ?: throw ServiceException("Error during finding vacancy with id: ${it.id}", 500)
                        this.body = it.body
                            ?: throw ServiceException("Error during finding vacancy with id: ${it.id}", 500)
                        this.skillTags = it.vacancySkills?.map { it.skill?.tag }
                            ?: throw ServiceException("Error during finding vacancy with id: ${it.id}", 500)
                        this.locationTags = it.locations?.map { it.id?.toBigDecimal() }
                            ?: throw ServiceException("Error during finding vacancy with id: ${it.id}", 500)
                    }
                }.toMutableList()
        )
    }

    override fun vacancyCreate(vacancyCreationView: VacancyCreationView): ResponseEntity<VacancyMetadataView> {
        if (vacancyCreationView.title == null || vacancyCreationView.body == null || vacancyCreationView.skillTags == null) {
            throw ServiceException("Wrong request!", 400)
        }

        val vacancyMeta = vacancyService.createVacancy(
            VacancyCreation(
            vacancyCreationView.title,
            vacancyCreationView.body,
            vacancyCreationView.skillTags,
            vacancyCreationView.locationIds.map { it.toLong() }
            )
        )

        return ResponseEntity.ok(
            VacancyMetadataView().apply {
                this.id = vacancyMeta.id.toBigDecimal()
            }
        )
    }
}