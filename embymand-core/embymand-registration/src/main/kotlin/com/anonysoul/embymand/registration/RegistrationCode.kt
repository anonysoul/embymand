package com.anonysoul.embymand.registration

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

/**
 * 注册码
 */
@Entity
class RegistrationCode(
    @field:Id
    val code: String,
) {
    var used: Boolean = false
        private set

    var usedAt: Instant? = null
        private set

    @CreationTimestamp
    @NotNull
    val createdAt: Instant = Instant.EPOCH

    fun use() {
        used = true
        usedAt = Instant.now()
    }
}
