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
        val vacancyCreation1 = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Kotlin", "Docker")
        )
        val vacancyCreation2 = VacancyCreation(
            "Javascript Developer",
            "Strong javascript developer",
            listOf("Javascript", "React", "Bootstrap")
        )
        val vacancyCreation3 = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Kotlin", "AWS")
        )
        val vacancyCreation4 = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Docker")
        )
        val vacancyCreation5 = VacancyCreation(
            "Javascript Developer",
            "Strong javascript developer",
            listOf("Javascript", "Vue.js", "Node.js")
        )
        val vacancyCreation6 = VacancyCreation(
            "Java/Scala Developer",
            "Strong java developer",
            listOf("Java", "Scala", "Gatling")
        )
        val vacancyCreation7 = VacancyCreation(
            "Java/Kotlin Developer",
            "Strong java developer",
            listOf("Java", "Kotlin", "Docker")
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
