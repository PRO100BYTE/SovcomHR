package com.pro100byte.config

import com.pro100byte.model.VacancyCreation
import com.pro100byte.service.VacancyService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class InitConfig(
    private val vacancyService: VacancyService,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun startup() {
        val vacancyCreation = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Kotlin", "Docker")
        )

        vacancyService.createVacancy(vacancyCreation)
    }
}
