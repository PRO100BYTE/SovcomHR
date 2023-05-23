package com.pro100byte.config

import com.pro100byte.location.model.LocationCreation
import com.pro100byte.location.service.LocationService
import com.pro100byte.vacancy.model.VacancyCreation
import com.pro100byte.vacancy.service.VacancyService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class InitConfig(
    private val vacancyService: VacancyService,
    private val locationService: LocationService,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun startup() {
        val locationCreation1 = LocationCreation(
            "Moscow",
            "Tverskaya",
            "25"
        )
        val locationCreation2 = LocationCreation(
            "Saint-Petersburg",
            "Nevskiy",
            "34"
        )

        val locId1 = locationService.createLocation(locationCreation1)
        val locId2 = locationService.createLocation(locationCreation2)

        val vacancyCreation1 = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Kotlin", "Docker"),
            listOf(locId1, locId2)
        )
        val vacancyCreation2 = VacancyCreation(
            "Javascript Developer",
            "Strong javascript developer",
            listOf("Javascript", "React", "Bootstrap"),
            listOf(locId2)
        )
        val vacancyCreation3 = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Kotlin", "AWS"),
            listOf(locId1, locId2)
        )
        val vacancyCreation4 = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Docker"),
            listOf(locId1)
        )
        val vacancyCreation5 = VacancyCreation(
            "Javascript Developer",
            "Strong javascript developer",
            listOf("Javascript", "Vue.js", "Node.js"),
            listOf(locId1, locId2)
        )
        val vacancyCreation6 = VacancyCreation(
            "Java/Scala Developer",
            "Strong java developer",
            listOf("Java", "Scala", "Gatling"),
            listOf(locId1, locId2)
        )
        val vacancyCreation7 = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Kotlin", "Docker"),
            listOf(locId2)
        )

        vacancyService.createVacancy(vacancyCreation1)
        vacancyService.createVacancy(vacancyCreation2)
        vacancyService.createVacancy(vacancyCreation3)
        vacancyService.createVacancy(vacancyCreation4)
        vacancyService.createVacancy(vacancyCreation5)
        vacancyService.createVacancy(vacancyCreation6)
        vacancyService.createVacancy(vacancyCreation7)
    }
}
