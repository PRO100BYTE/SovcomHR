package com.pro100byte.service

import com.pro100byte.exception.VacancyException
import com.pro100byte.model.*
import com.pro100byte.repository.ClosedVacancyRepository
import com.pro100byte.repository.OpenVacancyRepository
import com.pro100byte.repository.SkillRepository
import com.pro100byte.repository.VacancySkillTagRepository
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
) {
    private val pageSize = 10
    @Transactional(
        isolation = Isolation.READ_COMMITTED
    )
    fun createVacancy(vacancyCreation: VacancyCreation): VacancyMetadata {
        val vacancy = OpenVacancy().apply {
            this.title = vacancyCreation.title
            this.body = vacancyCreation.body
            this.date = System.currentTimeMillis()
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
        } ?: throw VacancyException("Couldnt create vacancy", 500)
    }

    @Transactional
    fun getVacancy(id: Long): Vacancy {
        return openVacancyRepository.findByIdOrNull(id)
            ?: throw VacancyException("Couldnt find vacancy with id: %s".format(id), 404)
    }

    @Transactional
    fun filteredVacancies(vacancyFilter: VacancyFilter): SearchedVacancies {
        val size = vacancyFilter.skillTags.size

        val vacancies = vacancyFilter.skillTags
            .map {
                skillRepository.findByIdOrNull(it) ?: return SearchedVacancies(
                    totalNumber = 0,
                    pages = listOf(),
                    firstPage = listOf()
                )
            }
            .flatMap {
                it.skillVacancies?.mapNotNull { it.vacancy?.id } ?: throw VacancyException("Smth went wrong", 500)
            }
            .groupBy {
                it
            }
            .filter { it.value.size == size }
            .map { it.key }

        return SearchedVacancies(
            totalNumber = vacancies.size.toLong(),
            pages = vacancies.chunked(pageSize),
            firstPage = vacancies.take(pageSize).map {
                openVacancyRepository.findByIdOrNull(it) ?: throw VacancyException("Smth went wrong", 500)
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
