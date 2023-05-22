package com.pro100byte.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class HttpConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
