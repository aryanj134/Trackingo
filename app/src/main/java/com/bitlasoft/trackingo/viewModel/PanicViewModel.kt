package com.bitlasoft.trackingo.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitlasoft.trackingo.domain.pojo.feedback_review.response.FeedbackResponse
import com.bitlasoft.trackingo.domain.repository.PanicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PanicViewModel(private val panicRepository: PanicRepository): ViewModel() {

    private val _panicResponse: MutableLiveData<FeedbackResponse> = MutableLiveData()
    val panicResponse: MutableLiveData<FeedbackResponse> get() = _panicResponse

    fun getPanicResponse(shortKey: String?, webApp: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = panicRepository.panicResponse(shortKey, webApp)
                if (response.isSuccessful) {
                    _panicResponse.postValue(response.body())
                } else {
                    _panicResponse.postValue(response.body())
                }
            } catch (e: Exception) {
                Log.e("tag", "Error submitting panic", e)
            }
        }
    }

}