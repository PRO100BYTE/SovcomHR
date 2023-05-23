package com.pro100byte.adapter

import com.pro100byte.api.ProfileApi
import com.pro100byte.util.JwtUtil
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal

@Component
class ProfileAdapter(
    private val restTemplate: RestTemplate,
    private val jwtUtil: JwtUtil,
) : ProfileApi {
    override fun createInitialProfile(): ResponseEntity<BigDecimal> {
        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(jwtUtil.createTechJWT())

        val httpEntity = HttpEntity<String>(httpHeaders)

        return restTemplate.exchange(
            "http://profile-service:8080/api/profile/initial",
            HttpMethod.POST,
            httpEntity,
            BigDecimal::class.java
        )
    }
}
