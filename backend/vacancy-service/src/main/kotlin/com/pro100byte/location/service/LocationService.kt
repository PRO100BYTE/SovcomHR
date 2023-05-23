package com.pro100byte.location.service

import com.pro100byte.exception.ServiceException
import com.pro100byte.location.model.Location
import com.pro100byte.location.model.LocationCreation
import com.pro100byte.location.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository,
) {

    fun getAllLocations(): List<Location> {
        return locationRepository.findAll()
    }

    fun createLocation(locationCreation: LocationCreation): Long {
        return locationRepository.save(Location().apply {
            this.city = locationCreation.city
            this.number = locationCreation.number
            this.street = locationCreation.street
        }).id ?: throw ServiceException("Couldnt save new location", 500)
    }
}