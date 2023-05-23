package com.pro100byte.config

import com.pro100byte.profile.model.ProfileEdit
import com.pro100byte.profile.service.ProfileService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class InitConfig(
    private val profileService: ProfileService,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun startUp() {
        val skillList = listOf("Java", "Kotlin", "Javascript", "Kotlin", "Python", "C++", "C#")

        (1..5)
            .map {
                profileService.createInitialProfile()
            }
            .map { ProfileEdit(
                id = it,
                firstName = "firstName$it",
                lastName = "lastName$it",
                skillTags = (1..3).map {
                    skillList.random()
                }
            )}
            .forEach {
                profileService.editProfile(it)
            }
    }
}