package com.anonysoul.embymand.integral

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

/**
 * 积分历史
 */
@Entity
class IntegralHistory(
    /**
     * telegram user id
     */
    @field:Id
    @field:Min(0)
    val userId: Long,
    /**
     * 类型
     */
    @field:Enumerated(EnumType.STRING)
    val type: Type = Type.CHECK_IN,
    /**
     * 增加或扣除的积分
     */
    var amount: Int = 0,
) {
    enum class Type {
        /**
         * 签到
         */
        CHECK_IN,

        /**
         * 点播
         */
        SUBSCRIBE,
    }

    @CreationTimestamp
    @NotNull
    val createdAt: Instant = Instant.EPOCH
}
