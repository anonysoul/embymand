package org.telegram.bot

import com.pengrad.telegrambot.TelegramBot
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetSocketAddress
import java.net.Proxy

@Configuration
class BotConfiguration(
    private val botProperties: BotProperties,
    private val proxyProperties: ProxyProperties,
) {
    @Bean
    fun bot(): TelegramBot {
        return if (proxyProperties.enabled) {
            val socketAddress = InetSocketAddress(proxyProperties.host, proxyProperties.port)
            val proxy = Proxy(proxyProperties.type, socketAddress)
            val okHttpClient = OkHttpClient().newBuilder().proxy(proxy).build()
            TelegramBot.Builder(botProperties.token).okHttpClient(okHttpClient).build()
        } else {
            TelegramBot(botProperties.token)
        }
    }
}
