package com.mis.management.cache

import com.mis.management.entity.User
import java.util.concurrent.ConcurrentHashMap

object UserCache {

    private val usermap:ConcurrentHashMap<String, User> = ConcurrentHashMap()

    fun get(key:String): User? {
        return usermap[key]
    }

    fun set(user:User){
        usermap.put(user.userName,user)
    }
    fun contains(key:String):Boolean{
        return usermap.contains(key)
    }
}