package com.bitlasoft.trackingo.domain.repository

import com.bitlasoft.trackingo.data.ApiInterface
import com.bitlasoft.trackingo.domain.pojo.short_key_pnr.request.ShortKeyValidationRequest
import com.bitlasoft.trackingo.domain.pojo.short_key_pnr.response.ShortKeyValidationResponse
import retrofit2.Response

class ShortKeyRepository(private val apiInterface: ApiInterface) {

    suspend fun validateShortKey(request: ShortKeyValidationRequest, baseUrl: String): Response<ShortKeyValidationResponse> =
        apiInterface.validateShortKeyApi(request)
}