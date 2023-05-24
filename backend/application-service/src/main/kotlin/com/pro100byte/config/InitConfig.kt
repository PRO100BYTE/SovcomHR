package com.pro100byte.config

import com.pro100byte.application.model.Apply
import com.pro100byte.application.service.ApplicationService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class InitConfig(
    private val applicationService: ApplicationService,
) {

    @EventListener(ApplicationReadyEvent::class)
    fun startUp() {
        val apply1 = Apply(
            1,
            1,
            "qwerty"
        )
        val apply2 = Apply(
            1,
            2,
            "qwerty"
        )
        val apply3 = Apply(
            1,
            3,
            "qwerty"
        )
        val apply4 = Apply(
            2,
            4,
            "qwerty"
        )
        val apply5 = Apply(
            3,
            4,
            "qwerty"
        )

        applicationService.apply(apply1)
        applicationService.apply(apply2)
        applicationService.apply(apply3)
        applicationService.apply(apply4)
        applicationService.apply(apply5)
    }
}