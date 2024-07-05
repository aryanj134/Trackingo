package com.bitlasoft.trackingo.domain.repository

import com.bitlasoft.trackingo.data.ApiInterface
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackResponse
import retrofit2.Response

class PanicRepository(private val apiInterface: ApiInterface) {
    suspend fun panicResponse(shortKey: String?, webApp: Boolean?): Response<FeedbackResponse> =
        apiInterface.panicResponse(shortKey, webApp)
}