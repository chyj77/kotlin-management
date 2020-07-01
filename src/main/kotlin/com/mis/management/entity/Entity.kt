package com.mis.management.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        var id: Int,

        @Column(name = "user_id")
        var userId: String,

        @Column(name = "user_name")
        var userName: String,

        @Column(name = "create_time")
        var createTime: String,

        @JsonIgnore
        @Column(name = "password")
        var password: String,

        @Transient
        var token: String,

        @Transient
        var roles: String,

        @Transient
        var pageIndex: Int = 0,
        @Transient
        var pageSize: Int = 10,
        @Transient
        var roleList: List<Role>


)

@Entity
@Table(name = "role")
data class Role(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        var id: Int,

        @Column(name = "role_id")
        var roleId: Long,

        @Column(name = "role_name")
        var roleName: String,

        @Column(name = "create_time")
        var createTime: String,

        @Transient
        var pageIndex: Int = 0,

        @Transient
        var pageSize: Int = 10

)

@Entity
@Table(name = "resource")
data class Resource(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        var id: Int,

        @Column(name = "resource_id")
        var resourceId: Long,

        @Column(name = "resource_name")
        var resourceName: String,

        @Column(name = "resource_path")
        var resourcePath: String,

        @Column(name = "resource_type")
        var resourceType: String,

        @Column(name = "create_time")
        var createTime: String,
        @Transient
        var roleList: List<Role>,

        @Transient
        var pageIndex: Int = 0,

        @Transient
        var pageSize: Int = 10

)

@Entity
@Table(name = "user_role")
data class UserRole(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        var id: Int,

        @Column(name = "user_id")
        var userId: String,

        @Column(name = "role_id")
        var roleId: Long,

        @Column(name = "create_time")
        var createTime: String
)

@Entity
@Table(name = "resource_role")
data class ResourceRole(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        var id: Int,

        @Column(name = "resource_id")
        var resourceId: Long,

        @Column(name = "role_id")
        var roleId: Long,

        @Column(name = "create_time")
        var createTime: String


)