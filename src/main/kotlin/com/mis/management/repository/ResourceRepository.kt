package com.mis.management.repository

import com.mis.management.entity.Resource
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ResourceRepository : JpaRepository<Resource, Int>, JpaSpecificationExecutor<Resource> {
}