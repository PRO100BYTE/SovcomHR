package com.pro100byte.util

import com.pro100byte.model.User
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.expireIn}") val expireIn: Long
) {
    private val EMAIL_CLAIM = "email"
    private val ROLES_CLAIM = "roles"
    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun createJwt(user: User): String {
        val claims = mapOf(EMAIL_CLAIM to user.email, ROLES_CLAIM to user.roles)

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + expireIn))
            .signWith(key)
            .compact()
    }

    fun validateJwt(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        }
    }

    fun decodeUsername(token: String): String {
        val body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return body.get(EMAIL_CLAIM, String::class.java)
    }

    fun decodeRoles(token: String): String {
        val body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return body.get(ROLES_CLAIM, String::class.java)
    }
}