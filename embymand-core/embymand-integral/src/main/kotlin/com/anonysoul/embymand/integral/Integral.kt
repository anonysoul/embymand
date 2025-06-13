package com.anonysoul.embymand.integral

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.Min

/**
 * 积分
 *
 * Q: 只有一个 value 字段，为什么不直接设计到 user 表中？
 * A: 用户表中的信息轻易不会发生变化，积分表中的信息会频繁变化，所以分开设计。
 */
@Entity
class Integral(
    /**
     * telegram user id
     */
    @field:Id
    @field:Min(0)
    val userId: Long,
) {
    /**
     * 积分
     */
    var score: Int = 0
        private set

    /**
     * 增加积分
     */
    fun add(amount: Int) {
        score += amount
    }

    /**
     * 减少积分
     */
    fun deduct(amount: Int) {
        score -= amount
    }
}
