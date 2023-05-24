package com.pro100byte.application.service

import com.pro100byte.application.model.*
import com.pro100byte.application.repository.ApplicationRepository
import com.pro100byte.application.repository.ProfileSearchRepository
import com.pro100byte.application.repository.VacancySearchRepository
import com.pro100byte.exception.ServiceException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
    private val profileSearchRepository: ProfileSearchRepository,
    private val vacancySearchRepository: VacancySearchRepository,
) {
    private val PAGE_SIZE = 10

    fun getApplicationById(id: Long): Application {
        return applicationRepository.findByIdOrNull(id)
            ?: throw ServiceException("Application with id: $id was not found", 404)
    }

    fun getAllMyApplications(myId: Long): SearchedApplications {
        val myApplications = profileSearchRepository.findAllByProfileId(myId)

        return SearchedApplications(
            totalnumber = myApplications.size.toLong(),
            applications = myApplications.chunked(PAGE_SIZE),
            firstApplications = myApplications.take(PAGE_SIZE).mapNotNull {
                applicationRepository.findByIdOrNull(it)
            }
        )
    }

    fun searchApplications(applicationSearch: ApplicationSearch): SearchedApplications {
        val applications = vacancySearchRepository
            .findAllByVacancyId(applicationSearch.vacancyId)

        return SearchedApplications(
            totalnumber = applications.size.toLong(),
            applications = applications.chunked(PAGE_SIZE),
            firstApplications = applications.take(PAGE_SIZE).mapNotNull {
                applicationRepository.findByIdOrNull(it)
            }
        )
    }

    @Transactional
    fun apply(apply: Apply): Application {
        val application = Application().apply {
            this.profileId = apply.profileId
            this.status = ApplicationStatus.NEW
            this.vacancyId = apply.vacancyId
        }
        applicationRepository.save(application)

        val vacancySearch = VacancySearch().apply {
            this.vacancySearchId = VacancySearchId().apply {
                this.vacancyId = apply.vacancyId
            }
            this.application = application
        }
        vacancySearchRepository.save(vacancySearch)

        val profileSearch = ProfileSearch().apply {
            this.profileSearchId = ProfileSearchId().apply {
                this.profileId = apply.profileId
            }
            this.application = application
        }
        profileSearchRepository.save(profileSearch)

        return application
    }
}
