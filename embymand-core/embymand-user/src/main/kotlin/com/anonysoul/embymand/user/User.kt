package com.anonysoul.embymand.user

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "\"user\"")
class User(
    /**
     * telegram user id
     */
    @field:Id
    @field:Min(0)
    val id: Long,

) {
    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER
        private set

    @NotNull
    var enabled = true
        private set

    @NotNull
    var locked = false
        private set

    @CreationTimestamp
    val createdAt: Instant = Instant.EPOCH

    fun changeRole(role: Role) {
        this.role = role
    }

    fun enable() {
        enabled = true
    }

    fun disable() {
        enabled = false
    }

    fun lock() {
        locked = true
    }

    fun unlock() {
        locked = false
    }
}
