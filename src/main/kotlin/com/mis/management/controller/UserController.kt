package com.mis.management.controller

import com.mis.management.domain.UserCommand
import com.mis.management.domain.UserQuery
import com.mis.management.entity.User
import com.mis.management.utils.ResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping
class UserController {

    @Autowired
    lateinit var userQuery: UserQuery
    @Autowired
    lateinit var userCommand: UserCommand

    @GetMapping("/login")
    fun login(userName: String, password: String): ResponseBody<Any> {
        var responseBody = ResponseBody<Any>()
        try {
            responseBody = userQuery.login(userName, password)
        } catch (e: IllegalStateException) {
            responseBody.message  = e.message!!
            responseBody.code = HttpStatus.BAD_REQUEST.value()
        }
        return responseBody
    }

    @PostMapping("/user/create")
    fun create(@RequestBody user: User): ResponseBody<Any> {
        return userCommand.create(user)
    }

    @PostMapping("/user/updatepwd")
    fun updatepwd(@RequestBody user: User): ResponseBody<Any> {
        val userName = user.userName
        val password = user.password
        return userCommand.updatePassword(userName,password)
    }
    @GetMapping("/user/query")
    fun query(user: User): ResponseBody<Any> {
        return userQuery.query(user)
    }
}