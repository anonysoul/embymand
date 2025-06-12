package com.anonysoul.embymand.emby

import embyclient.ApiClient
import embyclient.api.SessionsServiceApi
import embyclient.api.UserServiceApi
import embyclient.auth.ApiKeyAuth
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmbyConfiguration(
    @Value("\${emby.endpoint}")
    val endpoint: String,
    @Value("\${emby.api-key}")
    val apiKey: String,
) {
    init {
        val apiClient =
            ApiClient().apply {
                setBasePath(endpoint)
            }
        val authentication = apiClient.getAuthentication("apikeyauth") as ApiKeyAuth
        authentication.apiKey = apiKey
        embyclient.Configuration.setDefaultApiClient(apiClient)
    }

    @Bean
    fun userServiceApi() = UserServiceApi()

    @Bean
    fun sessionsServiceApi() = SessionsServiceApi()
}
