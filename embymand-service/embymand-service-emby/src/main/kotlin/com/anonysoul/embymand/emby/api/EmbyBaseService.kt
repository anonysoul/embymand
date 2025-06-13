package com.anonysoul.embymand.emby.api

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import embyclient.ApiResponse
import embyclient.Configuration
import embyclient.Pair

abstract class EmbyBaseService {
    internal class CustomQueryParams(
        @SerializedName("CustomQueryString")
        val customQueryString: String,
        @SerializedName("ReplaceUserId")
        val replaceUserId: Boolean,
    )

    protected class CustomQueryResponse(
        @SerializedName("colums")
        val colums: List<String>,
        @SerializedName("results")
        val results: List<List<String>>,
        @SerializedName("message")
        val message: String,
    )

    protected fun customQuery(
        sql: String,
        replaceUserId: Boolean,
    ): ApiResponse<CustomQueryResponse> {
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
        val localVarReturnType = object : TypeToken<CustomQueryResponse?>() {}.type
        return apiClient.execute(call, localVarReturnType)
    }
}
