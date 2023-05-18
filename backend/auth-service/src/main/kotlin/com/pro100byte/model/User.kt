package com.pro100byte.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "USERS")
class User {
    @Id
    var email: String? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "roles", nullable = false)
    var roles: String? = null
}
