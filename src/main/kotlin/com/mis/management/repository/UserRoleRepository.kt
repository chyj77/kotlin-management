package com.mis.management.repository

import com.mis.management.entity.Resource
import com.mis.management.entity.Role
import com.mis.management.entity.User
import com.mis.management.entity.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface UserRoleRepository : JpaRepository<UserRole, Int>, JpaSpecificationExecutor<UserRole> {

    @Modifying
    fun deleteByUserId(userId:String)

    @Query(value = "from Role role join UserRole rr on role.roleId = rr.roleId where rr.userId=?1")
    fun findAllByUserId(userId:String):List<Role>

    @Query(value = "from User r join UserRole rr on r.userId = rr.userId where rr.roleId=?1")
    fun findAllByRoleId(roleId:Long):List<User>
}