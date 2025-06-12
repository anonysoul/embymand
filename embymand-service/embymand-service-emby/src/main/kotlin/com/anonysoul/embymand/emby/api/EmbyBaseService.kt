package com.anonysoul.embymand.emby.api

import com.fasterxml.jackson.annotation.JsonProperty
import embyclient.Configuration
import embyclient.Pair

abstract class EmbyBaseService {
    internal class CustomQueryParams(
        @JsonProperty("CustomQueryString")
        val customQueryString: String,
        @JsonProperty("ReplaceUserId")
        val replaceUserId: Boolean
    )

    protected fun customQuery(sql: String, replaceUserId: Boolean) {
        val apiClient = Configuration.getDefaultApiClient()
        val apiUrl = "/user_usage_stats/submit_custom_query"
        val emptyPairList = emptyList<Pair>()
        val query = CustomQueryParams(sql, replaceUserId)
        val localVarHeaderParams= HashMap<String, String>()
        val localVarContentTypes = arrayOf("application/json", "application/xml")
        val localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes)
        localVarHeaderParams.put("Content-Type", localVarContentType)
        val localVarFormParams = emptyMap<String, Any>()
        val request = apiClient.buildCall(apiUrl, "POST", emptyPairList, emptyPairList, query, localVarHeaderParams, localVarFormParams, )
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);

    }
}
