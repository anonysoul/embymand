package com.anonysoul.embymand.integral

import org.springframework.data.repository.Repository
import java.time.Instant

interface IntegralHistoryRepository : Repository<IntegralHistory, Long> {
    fun save(history: IntegralHistory)
    fun existsByUserIdAndTypeAndCreatedAtAfter(userId: Long, type: IntegralHistory.Type, instant: Instant): Boolean
    fun countAllByCreatedAtAfter(instant: Instant): Long
}
