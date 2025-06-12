package com.anonysoul.embymand.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface UserRepository : Repository<User, Long> {
    fun findAll(pageable: Pageable): Page<User>

    fun findByIdIn(ids: Set<Long>): List<User>

    fun findById(id: Long): User?

    fun existsById(id: Long): Boolean

    fun save(user: User)

    fun saveAndFlush(user: User)

    fun deleteById(id: Long)
}
