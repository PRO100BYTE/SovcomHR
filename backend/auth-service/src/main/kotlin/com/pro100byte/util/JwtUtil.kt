package com.pro100byte.util

import com.pro100byte.model.User
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtil(
    @Value("\${jwt.expireIn}") val expireIn: Long,
    @Value("\${secret.key}") val secretKeyBase64: String,
) {
    private val ID_CLAIM = "id"
    private val EMAIL_CLAIM = "email"
    private val ROLES_CLAIM = "roles"
    private val key = SecretKeySpec(
        Base64.getDecoder().decode(secretKeyBase64),
        SignatureAlgorithm.HS256.jcaName
    )

    fun createJwt(user: User): String {
        val claims = mapOf(EMAIL_CLAIM to user.email, ROLES_CLAIM to user.roles, ID_CLAIM to user.id)

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

    fun checkExpiration(token: String): Boolean {
        val body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return body.expiration.after(Date(System.currentTimeMillis()))
    }

    fun decodeId(token: String): Long {
        val body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return body.get(ID_CLAIM, Long::class.java)
    }
}
