package com.mis.management.domain

import com.mis.management.cache.UserCache
import com.mis.management.entity.User
import com.mis.management.repository.UserRepository
import com.mis.management.repository.UserRoleRepository
import com.mis.management.utils.JWTUtils
import com.mis.management.utils.ResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@Service
class UserQuery {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userRoleRepository: UserRoleRepository

    fun login(userName: String, password: String): ResponseBody<Any> {
        check(!StringUtils.isEmpty(userName), { "请输入用户名" })
        check(!StringUtils.isEmpty(password), { "请输入密码" })
        var user = UserCache.get(userName)
        if (user == null) {
            try {
                user = userRepository.queryByUserNameAndPassword(userName, password)
            } catch (e: EmptyResultDataAccessException) {
                e.printStackTrace()
                return ResponseBody.fail("用户名或密码错误")
            }
        }
        if(password.equals(user.password)) {
            var token = JWTUtils.getToken(userName, password)
            user.token = token
            UserCache.set(user)
        }else{
            return ResponseBody.fail("用户名或密码错误")
        }
        return ResponseBody.success(user)
    }

    fun query(user:User):ResponseBody<Any> {
        val sort = Sort.by(Sort.Order.desc("createTime"))
        val pageable: Pageable = PageRequest.of(user.pageIndex,user.pageSize, sort)
        val specification = Specification.where({ root: Root<User?>, query: CriteriaQuery<*>?, builder: CriteriaBuilder ->
            val predicate = builder.conjunction()
            if(user.userName!=null) {
                predicate.expressions.add(builder.equal(root.get<Any>("userName"), user.userName))
            }
            predicate
        })
        val page: Page<User> = userRepository.findAll(specification, pageable)
        return ResponseBody.success(page)
    }

    fun queryById(id: Int): ResponseBody<Any> {
        var user = userRepository.findByIdOrNull(id)
        if (user == null) {
            return ResponseBody.fail("记录不存在")
        }else{
            var roleList = userRoleRepository.findAllByUserId(user.userId)
            user.roleList = roleList
        }
        return ResponseBody.success(user)
    }
}