package com.mis.management.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JWTUtils {

    var ttlMillis: Long = 24L * 60L * 60L * 1000L

    fun getToken(param1: String, param2: String): String {
        val expMillis: Long = System.currentTimeMillis() + ttlMillis
        val exp = Date(expMillis)
        val token = JWT.create().withAudience(param1).withExpiresAt(exp).sign(Algorithm.HMAC256(param2))
        return token
    }
}