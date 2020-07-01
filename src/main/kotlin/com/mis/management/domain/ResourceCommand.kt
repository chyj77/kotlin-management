package com.mis.management.domain

import com.mis.management.entity.Resource
import com.mis.management.entity.ResourceRole
import com.mis.management.repository.ResourceRepository
import com.mis.management.repository.ResourceRoleRepository
import com.mis.management.utils.ResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ResourceCommand {

    @Autowired
    lateinit var resourceRepository: ResourceRepository
    @Autowired
    lateinit var resourceRoleRepository : ResourceRoleRepository


    @Transactional
    fun create(rsource: Resource): ResponseBody<Any> {
        var count = resourceRepository.count()
        count = count + 1
        rsource.resourceId = count
        resourceRepository.save(rsource)
        if (!rsource.roleList.isNullOrEmpty()) {
            var list = ArrayList<ResourceRole>()
            rsource.roleList.forEach {
                var resourceRole = ResourceRole(0,count,it.roleId,"")
                list.plus(resourceRole)
            }
            resourceRoleRepository.saveAll(list)
        }
        return ResponseBody.success();
    }

    @Transactional
    fun update(rsource: Resource): ResponseBody<Any> {
        resourceRepository.save(rsource)
        if (!rsource.roleList.isNullOrEmpty()) {
            resourceRoleRepository.deleteByResourceId(rsource.resourceId)
            var list = ArrayList<ResourceRole>()
            rsource.roleList.forEach {
                var resourceRole = ResourceRole(0,rsource.resourceId,it.roleId,"")
                list.plus(resourceRole)
            }
            resourceRoleRepository.saveAll(list)
        }
        return ResponseBody.success();
    }
}