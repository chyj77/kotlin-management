package com.mis.management.domain

import com.mis.management.entity.Resource
import com.mis.management.repository.ResourceRepository
import com.mis.management.repository.ResourceRoleRepository
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
class ResourceQuery {

    @Autowired
    lateinit var resourceRepository: ResourceRepository

    @Autowired
    lateinit var resourceRoleRepository: ResourceRoleRepository


    fun query(resource: Resource): ResponseBody<Any> {
        val sort = Sort.by(Sort.Order.desc("createTime"))
        val pageable: Pageable = PageRequest.of(resource.pageIndex, resource.pageSize, sort)
        val specification = Specification.where({ root: Root<Resource?>, query: CriteriaQuery<*>?, builder: CriteriaBuilder ->
            val predicate = builder.conjunction()
            if (resource.resourceName != null) {
                predicate.expressions.add(builder.equal(root.get<Any>("resourceName"), resource.resourceName))
            }
            predicate
        })
        val page: Page<Resource> = resourceRepository.findAll(specification, pageable)
        return ResponseBody.success(page)
    }

    fun queryById(id: Int): ResponseBody<Any> {
        var resource = resourceRepository.findByIdOrNull(id)
        if (resource == null) {
            return ResponseBody.fail("记录不存在")
        }else{
            var roleList = resourceRoleRepository.findAllByResourceId(resource.resourceId)
            resource.roleList = roleList
        }
        return ResponseBody.success(resource)
    }
}