package com.anonysoul.embymand.emby.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.reflect.TypeToken
import embyclient.Configuration
import embyclient.Pair
import embyclient.model.UserDto

abstract class EmbyBaseService {
    internal class CustomQueryParams(
        @JsonProperty("CustomQueryString")
        val customQueryString: String,
        @JsonProperty("ReplaceUserId")
        val replaceUserId: Boolean,
    )

    protected fun customQuery(
        sql: String,
        replaceUserId: Boolean,
    ) {
        val apiClient = Configuration.getDefaultApiClient()
        val apiUrl = "/user_usage_stats/submit_custom_query"
        val localVarQueryParams = mutableListOf<Pair>()
        val localVarCollectionQueryParams = mutableListOf<Pair>()
        val query = CustomQueryParams(sql, replaceUserId)
        val localVarHeaderParams = mutableMapOf<String, String>()
        val localVarContentTypes = arrayOf("application/json", "application/xml")
        val localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes)
        localVarHeaderParams.put("Content-Type", localVarContentType)
        val localVarFormParams = emptyMap<String, Any>()
        val localVarAuthNames = arrayOf("apikeyauth", "embyauth")
        val call =
            apiClient.buildCall(
                apiUrl,
                "POST",
                localVarQueryParams,
                localVarCollectionQueryParams,
                query,
                localVarHeaderParams,
                localVarFormParams,
                localVarAuthNames,
                null,
            )
        val localVarReturnType = object : TypeToken<UserDto?>() {}.type
        apiClient.execute<UserDto?>(call, localVarReturnType)
    }
}
