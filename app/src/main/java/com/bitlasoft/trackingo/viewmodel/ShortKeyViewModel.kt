package com.bitlasoft.trackingo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitlasoft.trackingo.domain.pojo.short_key_pnr.request.ShortKeyValidationRequest
import com.bitlasoft.trackingo.domain.pojo.short_key_pnr.response.ShortKeyValidationResponse
import com.bitlasoft.trackingo.domain.repository.ShortKeyRepository
import com.bitlasoft.trackingo.utils.BaseUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class ShortKeyViewModel(private val repository: ShortKeyRepository) : ViewModel() {

    private val _validationResponse = MutableLiveData<String>()
    val validationResponse: LiveData<String> get() = _validationResponse

    fun validateShortKeyOrPnr(shortKey: String, pnrNumber: String) {
        viewModelScope.launch {
            try {
                val request = ShortKeyValidationRequest(shortKey, pnrNumber)
                val response = withContext(Dispatchers.IO) {
                    repository.validateShortKey(request, BaseUrl.SHORT_KEY_PNR)
                }

                handleValidationResponse(response)
            } catch (e: IOException) {
                _validationResponse.postValue("Network error: ${e.message}")
            } catch (e: UnknownHostException) {
                _validationResponse.postValue("Unable to resolve host: ${e.message}")
            } catch (e: Exception) {
                _validationResponse.postValue("Error: ${e.message}")
            }
        }
    }

    private fun handleValidationResponse(response: Response<ShortKeyValidationResponse>) {
        if (!response.isSuccessful) {
            _validationResponse.postValue("Error: ${response.code()}")
            return
        }

        val validationResponse = response.body()
        if (validationResponse == null) {
            _validationResponse.postValue("Invalid response from server")
            return
        }

        val message = validationResponse.message
        if (message.isNullOrEmpty()) {
            _validationResponse.postValue("Empty response message")
            return
        }

        if (message.contains("successful", ignoreCase = true)) {
            _validationResponse.postValue("Validation successful")
        } else {
            _validationResponse.postValue(message)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}

//private fun validateShortKeyOrPnr(shortKey: String, pnrNumber: String) {
//    if (!isInternetAvailable(requireContext())) {
//        showToast("No internet connection")
//        return
//    }
//
//    val request = ShortKeyValidationRequest(shortKey, pnrNumber)
//
//    lifecycleScope.launch {
//        try {
//            val response = withContext(Dispatchers.IO) {
//                provideRetrofit(BaseUrl.SHORT_KEY_PNR, get(), get()).create(ApiInterface::class.java).validateShortKey(request)
//            }
//
//            Log.d("ShortKeyFragment", "Response Code: ${response.code()}")
//            Log.d("ShortKeyFragment", "Response Body: ${response.body()}")
//
//            if (response.isSuccessful) {
//                val validationResponse = response.body()
//                handleValidationResponse(validationResponse)
//            } else {
//                showToast("Error: ${response.code()}")
//            }
//        } catch (e: IOException) {
//            showToast("Network error: ${e.message}")
//        } catch (e: HttpException) {
//            showToast("HTTP error: ${e.code()}")
//        } catch (e: UnknownHostException) {
//            showToast("Unable to resolve host: ${e.message}")
//        } catch (e: Exception) {
//            showToast("Error: ${e.message}")
//        }
//    }
//}
//
//private fun handleValidationResponse(response: ShortKeyValidationResponse?) {
//    if (response == null) {
//        showToast("Invalid response from server")
//        return
//    }
//
//    val message = response.message
//    if (message.isNullOrEmpty()) {
//        showToast("Empty response message")
//        return
//    }
//
//    Log.d("ShortKeyFragment", "Response message: $message")
//
//    if (message.contains("successful", ignoreCase = true)) {
//        showToast("Validation successful")
//        findNavController().navigate(R.id.action_shortKeyFragment_to_secondFragment)
//    } else {
//        showToast(message)
//    }
//}
//
//private fun isInternetAvailable(context: Context): Boolean {
//    val connectivityManager =
//        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//
//    if (connectivityManager != null) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            val capabilities =
//                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
//                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
//        } else {
//            val activeNetworkInfo = connectivityManager.activeNetworkInfo
//            return activeNetworkInfo != null && activeNetworkInfo.isConnected
//        }
//    }
//    return false
//}
