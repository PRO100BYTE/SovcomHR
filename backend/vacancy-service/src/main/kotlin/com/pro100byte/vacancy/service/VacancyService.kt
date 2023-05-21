package com.pro100byte.vacancy.service

import com.pro100byte.exception.ServiceException
import com.pro100byte.location.repository.LocationRepository
import com.pro100byte.skill.model.Skill
import com.pro100byte.vacancy.repository.ClosedVacancyRepository
import com.pro100byte.vacancy.repository.OpenVacancyRepository
import com.pro100byte.skill.repository.SkillRepository
import com.pro100byte.vacancy.model.*
import com.pro100byte.vacancy.repository.VacancySkillTagRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class VacancyService(
    private val openVacancyRepository: OpenVacancyRepository,
    private val closedVacancyRepository: ClosedVacancyRepository,
    private val skillRepository: SkillRepository,
    private val vacancySkillTagRepository: VacancySkillTagRepository,
    private val locationRepository: LocationRepository,
) {
    private val pageSize = 10
    @Transactional(
        isolation = Isolation.READ_COMMITTED
    )
    fun createVacancy(vacancyCreation: VacancyCreation): VacancyMetadata {
        val locations = locationRepository.findAllById(vacancyCreation.locations)

        if (locations.isEmpty()) {
            throw ServiceException("Locations with ids: ${vacancyCreation.locations} were found!", 400)
        }

        val vacancy = OpenVacancy().apply {
            this.title = vacancyCreation.title
            this.body = vacancyCreation.body
            this.date = System.currentTimeMillis()
            this.locations = locations
        }

        openVacancyRepository.save(vacancy)

        val skills = vacancyCreation.skillTags.map {
            skillRepository.save(Skill().apply {
                this.tag = it.lowercase()
            })
        }

        val vacancySkillTags = skills
            .map {
                val vacancySkillTag = VacancySkillTag().apply {
                    this.id = VacancySkillTagId().apply {
                        this.vacancyId = vacancy.id
                        this.skillTag = it.tag
                    }
                }
                vacancySkillTag
            }

        vacancySkillTagRepository.saveAll(vacancySkillTags)

        vacancy.id?.let {
            return VacancyMetadata(it)
        } ?: throw ServiceException("Couldnt create vacancy", 500)
    }

    @Transactional
    fun getVacancy(id: Long): Vacancy {
        return openVacancyRepository.findByIdOrNull(id)
            ?: throw ServiceException("Couldnt find vacancy with id: %s".format(id), 404)
    }

    @Transactional
    fun filteredVacancies(vacancyFilter: VacancyFilter): SearchedVacancies {
        val tagsCount = (vacancyFilter.skillTags?.size ?: 0) + (vacancyFilter.locationTags?.size ?: 0)

        if (tagsCount == 0) {
            return topVacancies()
        }

        val skillVacancyIds = vacancyFilter.skillTags
            ?.map {
                skillRepository.findByIdOrNull(it.lowercase()) ?: return SearchedVacancies(
                    totalNumber = 0,
                    pages = listOf(),
                    firstPage = listOf()
                )
            }
            ?.flatMap {
                it.skillVacancies?.mapNotNull { it.vacancy?.id }
                    ?: throw ServiceException.smthWentWrong()
            } ?: listOf()

        val locationVacancyIds = vacancyFilter.locationTags
            ?.map {
                locationRepository.findByIdOrNull(it)
                    ?: throw ServiceException("Validation error. No location with id: $it", 400)
            }
            ?.flatMap {
                it.vacancies?.mapNotNull { it.id }
                    ?: throw ServiceException.smthWentWrong()
            } ?: listOf()

        val grouped = (skillVacancyIds + locationVacancyIds).groupBy { it }.filter { it.value.size == tagsCount }
        val vacancies = grouped.map { it.key }

        return SearchedVacancies(
            totalNumber = vacancies.size.toLong(),
            pages = vacancies.chunked(pageSize),
            firstPage = vacancies.take(pageSize).map {
                openVacancyRepository.findByIdOrNull(it) ?: throw ServiceException.smthWentWrong()
            }
        )
    }

    @Transactional
    fun topVacancies(): SearchedVacancies {
        return SearchedVacancies(
            totalNumber = openVacancyRepository.count(),
            pages = openVacancyRepository.find1000NextVacancies(Pageable.ofSize(1000)).chunked(pageSize),
            firstPage = openVacancyRepository.findAll(Pageable.ofSize(pageSize)).content
        )
    }

    @Transactional
    fun getVacanciesByIds(ids: List<Long>): List<Vacancy> {
        return openVacancyRepository.findAllById(ids)
    }
}
