package com.anonysoul.embymand.integral

/**
 * 积分服务
 */
interface IntegralService {
    /**
     * 获取用户积分
     */
    fun get(userId: Long): Int

    /**
     * 签到得积分
     */
    fun checkIn(userId: Long): CheckInResultTO

    /**
     * 扣除积分
     *
     * @param amount 积分数量（必须是正数）
     */
    fun deduct(
        userId: Long,
        amount: Int,
    ): DeductResultTO
}
