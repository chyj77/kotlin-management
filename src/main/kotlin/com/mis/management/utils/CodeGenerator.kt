package com.mis.management.utils

import java.util.*

object CodeGenerator {
    fun idGenerator(): String {
        var uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")
        val now = System.currentTimeMillis()
        val nowtime = java.lang.Long.toString(now)
        uuid = uuid + nowtime.substring(nowtime.length - 4)
        return uuid
    }

    fun smsCode(): String {
        return (Random().nextInt(899999) + 100000).toString()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(idGenerator())
    }
}