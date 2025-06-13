package com.anonysoul.embymand.bot

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import jakarta.validation.ValidatorFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.telegram.bot.BotProperties

@SpringBootApplication(
    scanBasePackages = [
        "com.anonysoul.embymand",
        "org.telegram.bot",
    ],
)
@EnableJpaRepositories(
    basePackages = ["com.anonysoul.embymand"],
)
@EntityScan(basePackages = ["com.anonysoul.embymand"])
class BotApp(
    private val bot: TelegramBot,
    private val botProperties: BotProperties,
    private val eventParser: EventParser,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    init {
        listenEvent()
    }

    /**
     * 让 Hibernate 保存的时候使用 Spring 的 Validator 进行有效性验证, 因为 Spring 的 Validator 支持注入依赖
     */
    @Bean
    fun hibernatePropertiesCustomizer(validator: ValidatorFactory): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer { hibernateProperties ->
            hibernateProperties["jakarta.persistence.validation.factory"] = validator
        }
    }

    /**
     * 监听 telegram bot api 事件
     */
    fun listenEvent() {
        bot.setUpdatesListener { updates ->
            updates.forEach { update ->
                try {
                    eventParser.parse(update)?.let { event ->
                        applicationEventPublisher.publishEvent(event)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }
}

fun main(args: Array<String>) {
    runApplication<BotApp>(*args)
}
