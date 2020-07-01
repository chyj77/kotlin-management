package com.mis.management.domain

import com.mis.management.cache.UserCache
import com.mis.management.entity.User
import com.mis.management.entity.UserRole
import com.mis.management.repository.UserRepository
import com.mis.management.repository.UserRoleRepository
import com.mis.management.utils.CodeGenerator
import com.mis.management.utils.ResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserCommand {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userRoleRepository: UserRoleRepository

    @Transactional
    fun create(user: User): ResponseBody<Any> {
        val uuid = CodeGenerator.idGenerator()
        user.userId = uuid
        var cacheUser = UserCache.get(user.userName)
        if (cacheUser == null) {
            try {
                cacheUser = userRepository.queryByUserName(user.userName)
            } catch (e: EmptyResultDataAccessException) {
                var roleList = user.roleList
                if (!roleList.isNullOrEmpty()) {
                    var roles = ArrayList<UserRole>()
                    roleList.forEach {
                        var userRole = UserRole(0,user.userId,it.roleId,"")
                        roles.plus(userRole)
                    }
                    userRoleRepository.saveAll(roles)
                }
                userRepository.save(user)
                UserCache.set(user)
                return ResponseBody.success();
            }
        }
        return ResponseBody.fail("username已经存在");
    }

    fun updatePassword(userName: String, password: String): ResponseBody<Any> {
        var user = UserCache.get(userName)
        if (user != null) {
            user.password  = password!!
        }else{
            return ResponseBody.fail(userName+" 不存在");
        }
        userRepository.save(user!!)
        UserCache.set(user!!)
        return ResponseBody.success();
    }

    @Transactional
    fun updateRole(user: User): ResponseBody<Any> {
        var roleList = user.roleList
        if (!roleList.isNullOrEmpty()) {
            userRoleRepository.deleteByUserId(user.userId)
            var roles = ArrayList<UserRole>()
            roleList.forEach {
                var userRole = UserRole(0,user.userId,it.roleId,"")
                roles.plus(userRole)
            }
            userRoleRepository.saveAll(roles)
        }
        userRepository.save(user!!)
        UserCache.set(user!!)
        return ResponseBody.success();
    }
}