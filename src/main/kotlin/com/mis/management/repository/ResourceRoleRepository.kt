package com.mis.management.repository

import com.mis.management.entity.Resource
import com.mis.management.entity.ResourceRole
import com.mis.management.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ResourceRoleRepository : JpaRepository<ResourceRole, Int>, JpaSpecificationExecutor<ResourceRole> {

    @Modifying
    fun deleteByResourceId(resouceId:Long)

    @Query(value = "from Role role join ResourceRole rr on role.roleId = rr.roleId where rr.resourceId=?1")
    fun findAllByResourceId(resouceId:Long):List<Role>

    @Query(value = "from Resource r join ResourceRole rr on r.resourceId = rr.resourceId where rr.roleId=?1")
    fun findAllByRoleId(resouceId:Long):List<Resource>
}