package com.anonysoul.embymand.integral

data class CheckInResultTO (
    /**
     * 今日签到排名
     */
    val ranking: Long,
    /**
     * 签到获得的积分
     */
    val amount: Int,
    /**
     * 当前总积分
     */
    val score: Int
)
