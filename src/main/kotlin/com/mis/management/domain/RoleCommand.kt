package com.mis.management.domain

import com.mis.management.entity.Role
import com.mis.management.repository.RoleRepository
import com.mis.management.utils.ResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleCommand {

    @Autowired
    lateinit var roleRepository:RoleRepository

    fun create(role:Role): ResponseBody<Any> {
        var count = roleRepository.count()
        role.roleId = count+1
        roleRepository.save(role)
        return ResponseBody.success()
    }

    fun update(role:Role): ResponseBody<Any> {
        roleRepository.save(role)
        return ResponseBody.success()
    }
}