package com.pro100byte.filter

import com.pro100byte.util.JwtUtil
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val headerValue = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (headerValue == null || headerValue.isEmpty() || !headerValue.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = headerValue.split(" ")[1]

        if (!jwtUtil.validateJwt(token)) {
            filterChain.doFilter(request, response)
            return
        }

        if (!jwtUtil.checkExpiration(token)) {
            filterChain.doFilter(request, response)
            return
        }

        val user = User.builder()
            .username(jwtUtil.decodeUsername(token))
            .password("")
            .roles(*jwtUtil.decodeRoles(token).split(",").toTypedArray())
            .build()

        val authentication = UsernamePasswordAuthenticationToken(
            user,
            null,
            user.authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}
