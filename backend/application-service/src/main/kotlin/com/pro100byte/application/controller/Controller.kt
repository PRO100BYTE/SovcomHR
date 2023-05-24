package com.pro100byte.application.controller

import com.pro100byte.api.ApplicationApi
import com.pro100byte.application.model.ApplicationSearch
import com.pro100byte.application.model.Apply
import com.pro100byte.application.service.ApplicationService
import com.pro100byte.dto.ApplicationSearchView
import com.pro100byte.dto.ApplicationView
import com.pro100byte.dto.ApplyView
import com.pro100byte.dto.SearchedApplicationView
import com.pro100byte.exception.ServiceException
import com.pro100byte.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import javax.servlet.http.HttpServletRequest

@RestController
class Controller(
    private val httpServletRequest: HttpServletRequest,
    private val applicationService: ApplicationService,
    private val jwtUtil: JwtUtil,
) : ApplicationApi {
    override fun getAllApplications(applicationSearchView: ApplicationSearchView): ResponseEntity<SearchedApplicationView> {
        val searchedApplication = applicationService
            .searchApplications(ApplicationSearch(applicationSearchView.vacancyId.toLong()))
        return ResponseEntity.ok(
            SearchedApplicationView().apply {
                this.totalnumber = searchedApplication.totalnumber.toBigDecimal()
                this.applications = searchedApplication.applications.map { it.map { it.toBigDecimal() } }
                this.firstApplications = searchedApplication.firstApplications.map {
                    ApplicationView().apply {
                        this.id = it.id?.toBigDecimal()
                        this.vacancyId = it.vacancyId?.toBigDecimal()
                        this.profileId = it.profileId?.toBigDecimal()
                        this.status = it.status.name
                    }
                }
            }
        )
    }

    override fun getAllMyApplications(): ResponseEntity<SearchedApplicationView> {

        val searchedApplication = applicationService
            .getAllMyApplications(jwtUtil.decodeId(jwtToken()))
        return ResponseEntity.ok(
            SearchedApplicationView().apply {
                this.totalnumber = searchedApplication.totalnumber.toBigDecimal()
                this.applications = searchedApplication.applications.map { it.map { it.toBigDecimal() } }
                this.firstApplications = searchedApplication.firstApplications.map {
                    ApplicationView().apply {
                        this.id = it.id?.toBigDecimal()
                        this.vacancyId = it.vacancyId?.toBigDecimal()
                        this.profileId = it.profileId?.toBigDecimal()
                        this.status = it.status.name
                    }
                }
            }
        )
    }

    override fun getApplicationById(id: BigDecimal): ResponseEntity<ApplicationView> {
        val result = applicationService.getApplicationById(id.toLong())
        return ResponseEntity.ok(
            ApplicationView().apply {
                this.id = result.id?.toBigDecimal()
                this.vacancyId = result.vacancyId?.toBigDecimal()
                this.profileId = result.profileId?.toBigDecimal()
                this.status = result.status.name
            }
        )
    }

    override fun apply(applyView: ApplyView): ResponseEntity<ApplicationView> {
        val result = applicationService.apply(Apply(
            applyView.vacancyId.toLong(),
            jwtUtil.decodeId(jwtToken()),
            applyView.coverLetter
        ))
        return ResponseEntity.ok(
            ApplicationView().apply {
                this.id = result.id?.toBigDecimal()
                this.vacancyId = result.vacancyId?.toBigDecimal()
                this.profileId = result.profileId?.toBigDecimal()
                this.status = result.status.name
            }
        )
    }

    private fun jwtToken(): String {
        val headerValue = httpServletRequest.getHeader("Authorization")
            ?: throw ServiceException.smthWentWrong()
        return headerValue.split(" ").getOrNull(1)
            ?: throw ServiceException.smthWentWrong()
    }
}
