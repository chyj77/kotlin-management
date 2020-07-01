package com.mis.management.repository

import com.mis.management.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RoleRepository : JpaRepository<Role, Int>, JpaSpecificationExecutor<Role> {
}