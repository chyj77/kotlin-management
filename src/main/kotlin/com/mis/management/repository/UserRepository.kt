package com.mis.management.repository

import com.mis.management.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRepository : JpaRepository<User, Int>, JpaSpecificationExecutor<User> {

    fun queryByUserNameAndPassword(userName:String,password:String):User

    fun queryByUserName(userName:String):User
}