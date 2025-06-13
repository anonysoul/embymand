package com.anonysoul.embymand.integral.impl

import com.anonysoul.embymand.common.BusinessException
import com.anonysoul.embymand.integral.CheckInResultTO
import com.anonysoul.embymand.integral.DeductResultTO
import com.anonysoul.embymand.integral.Integral
import com.anonysoul.embymand.integral.IntegralHistory
import com.anonysoul.embymand.integral.IntegralHistoryRepository
import com.anonysoul.embymand.integral.IntegralRepository
import com.anonysoul.embymand.integral.IntegralService
import jakarta.transaction.Transactional
import jakarta.validation.constraints.Min
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId

@Service
class IntegralServiceImpl(
    private val integralRepository: IntegralRepository,
    private val integralHistoryRepository: IntegralHistoryRepository,
    /**
     * 时区
     */
    @Value("\${embymand.timezone}")
    val timezone: String,
    /**
     * 每次签到获得的积分
     */
    @Value("\${embymand.integral.check-in.score:5}")
    private val amountPreCheckIn: Int,
) : IntegralService {
    private val zone = ZoneId.of(timezone)

    override fun get(userId: Long): Int {
        val integral = integralRepository.findByUserId(userId) ?: throw IllegalArgumentException("用户不存在")
        return integral.score
    }

    @Transactional
    @Synchronized
    override fun checkIn(userId: Long): CheckInResultTO {
        // 今天的0点0分
        val todayOfStart = Instant.now().atZone(zone).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant()
        // 今天已经签到了吗
        val alreadyCheckIn =
            integralHistoryRepository.existsByUserIdAndTypeAndCreatedAtAfter(
                userId,
                IntegralHistory.Type.CHECK_IN,
                todayOfStart,
            )
        if (alreadyCheckIn) {
            throw BusinessException("今天已经签到过了")
        }
        // 获取今天已经有多少用户签到了
        val ranking = integralHistoryRepository.countAllByCreatedAtAfter(todayOfStart)
        var integral = integralRepository.findByUserId(userId)
        if (integral == null) {
            integral = Integral(userId)
        }
        integral.add(amountPreCheckIn)
        integralRepository.save(integral)
        val history = IntegralHistory(userId, IntegralHistory.Type.CHECK_IN, amountPreCheckIn)
        integralHistoryRepository.save(history)
        return CheckInResultTO(ranking + 1, amountPreCheckIn, integral.score)
    }

    @Transactional
    @Synchronized
    override fun deduct(
        userId: Long,
        @Min(0) amount: Int,
    ): DeductResultTO {
        val integral = integralRepository.findByUserId(userId) ?: throw IllegalArgumentException("用户不存在")
        integral.deduct(amount)
        integralRepository.save(integral)
        return DeductResultTO(amount, integral.score)
    }
}
