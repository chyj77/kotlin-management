package com.mis.management.domain

import com.mis.management.entity.Role
import com.mis.management.entity.User
import com.mis.management.repository.ResourceRoleRepository
import com.mis.management.repository.RoleRepository
import com.mis.management.repository.UserRoleRepository
import com.mis.management.utils.ResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@Service
class RoleQuery {

    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var userRoleRepository: UserRoleRepository
    @Autowired
    lateinit var resourceRoleRepository : ResourceRoleRepository


    fun query(role: Role):ResponseBody<Any> {
        val sort = Sort.by(Sort.Order.desc("createTime"))
        val pageable: Pageable = PageRequest.of(role.pageIndex,role.pageSize, sort)
        val specification = Specification.where({ root: Root<Role?>, query: CriteriaQuery<*>?, builder: CriteriaBuilder ->
            val predicate = builder.conjunction()
            if(role.roleName!=null) {
                predicate.expressions.add(builder.equal(root.get<Any>("roleName"), role.roleName))
            }
            predicate
        })
        val page: Page<Role> = roleRepository.findAll(specification, pageable)
        return ResponseBody.success(page)
    }

    fun queryRoleById(id: Int):ResponseBody<Any> {
        var role= roleRepository.findByIdOrNull(id)
        return ResponseBody.success(role!!)
    }

    fun queryUser(roleId: Long):ResponseBody<Any> {
        var users= userRoleRepository.findAllByRoleId(roleId).orEmpty()
        return ResponseBody.success(users!!)
    }
    fun queryResource(roleId: Long):ResponseBody<Any> {
        var resources= resourceRoleRepository.findAllByRoleId(roleId).orEmpty()
        return ResponseBody.success(resources!!)
    }
}