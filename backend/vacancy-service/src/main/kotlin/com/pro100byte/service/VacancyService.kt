package com.pro100byte.service

import com.pro100byte.exception.VacancyException
import com.pro100byte.model.*
import com.pro100byte.repository.ClosedVacancyRepository
import com.pro100byte.repository.OpenVacancyRepository
import com.pro100byte.repository.SkillRepository
import com.pro100byte.repository.VacancySkillTagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.sql.Date

@Service
class VacancyService(
    private val openVacancyRepository: OpenVacancyRepository,
    private val closedVacancyRepository: ClosedVacancyRepository,
    private val skillRepository: SkillRepository,
    private val vacancySkillTagRepository: VacancySkillTagRepository,
) {
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
                this.skillTag = it
            })
        }

        val vacancySkillTags = skills
            .map {
                VacancySkillTag().apply {
                    this.id = VacancySkillTagId().apply {
                        this.vacancyId = vacancy.id
                        this.skillTag = it.skillTag
                    }
                    this.vacancy = vacancy
                    this.skill = it
                }
            }.map {
                vacancySkillTagRepository.save(it)
            }

        vacancy.vacancySkills = vacancy.vacancySkills?.plus(vacancySkillTags)

        vacancy.id?.let {
            return VacancyMetadata(it)
        } ?: throw VacancyException("Couldnt create vacancy", 500)
    }

    @Transactional
    fun getVacancy(id: Long): Vacancy {
        return openVacancyRepository.findByIdOrNull(id)
            ?: throw VacancyException("Couldnt find vacancy with id: %s".format(id), 404)
    }
}
