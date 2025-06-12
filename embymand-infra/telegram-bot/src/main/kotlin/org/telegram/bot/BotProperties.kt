package org.telegram.bot

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import kotlin.properties.Delegates

@Configuration
@ConfigurationProperties(prefix = "bot.identity")
class BotProperties {
    var creator by Delegates.notNull<Long>()
    var token by Delegates.notNull<String>()
    var username by Delegates.notNull<String>()

    val id: Long
        get() = token.split(":")[0].toLong()
}
