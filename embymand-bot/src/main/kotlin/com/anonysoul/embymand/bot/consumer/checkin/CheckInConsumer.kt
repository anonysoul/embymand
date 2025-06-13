package com.anonysoul.embymand.bot.consumer.checkin

import com.anonysoul.embymand.bot.consumer.BaseConsumer
import com.anonysoul.embymand.bot.consumer.checkin.event.CheckInEvent
import com.anonysoul.embymand.common.BusinessException
import com.anonysoul.embymand.integral.IntegralService
import com.pengrad.telegrambot.TelegramBot
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CheckInConsumer(
    private val bot: TelegramBot,
    private val integralService: IntegralService,
    @Value("\${embymand.integral.unit}")
    private val integralUnit: String,
) : BaseConsumer(bot) {
    @EventListener
    fun onEvent(event: CheckInEvent) {
        try {
            val result = integralService.checkIn(event.chatId)
            sendCallbackQueryAnswer(event.callbackQueryId, "签到成功", false)
            val text =
                """
                🎉 签到成功
                获得 ${result.amount} $integralUnit，当前 ${result.score} $integralUnit
                今日签到排名： ${result.ranking}
                """.trimIndent()
            sendTextMessage(event.chatId, text, event.originMessageId)
        } catch (e: BusinessException) {
            sendCallbackQueryAnswer(event.callbackQueryId, e.message!!, false)
        }
    }
}
