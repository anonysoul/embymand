package com.anonysoul.embymand.integral

import org.springframework.data.repository.Repository

interface IntegralRepository : Repository<Integral, Long> {
    fun save(integral: Integral)
    fun findByUserId(id: Long): Integral?
}
