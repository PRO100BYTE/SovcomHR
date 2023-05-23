package com.pro100byte.config

import com.pro100byte.filter.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JwtFilter,
) {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.POST,  "/api/profile/initial").hasAnyRole("ADMIN", "TECH")
            .antMatchers(HttpMethod.POST, "/api/profile/create").hasAnyRole("ADMIN", "HR")
            .antMatchers(HttpMethod.GET, "/api/profile/search").hasAnyRole("ADMIN", "HR")
            .antMatchers(HttpMethod.GET, "/api/profile/myProfile", "/api/profile/**").hasAnyRole("ADMIN", "HR", "USER")
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }
}
