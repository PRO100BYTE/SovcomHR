package com.pro100byte.location.controller

import com.pro100byte.api.LocationApi
import com.pro100byte.dto.LocationView
import com.pro100byte.exception.ServiceException
import com.pro100byte.location.service.LocationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class LocationController(
    private val locationService: LocationService,
) : LocationApi {
    override fun getAllLocations(): ResponseEntity<MutableList<LocationView>> {
        return ResponseEntity.ok(
            locationService.getAllLocations().map { LocationView().apply {
                this.id = it.id?.toBigDecimal()
                    ?: throw ServiceException.smthWentWrong()
                this.city = it.city
                    ?: throw ServiceException.smthWentWrong()
                this.number = it.number
                    ?: throw ServiceException.smthWentWrong()
                this.street = it.street
                    ?: throw ServiceException.smthWentWrong()
            }}.toMutableList()
        )
    }
}
