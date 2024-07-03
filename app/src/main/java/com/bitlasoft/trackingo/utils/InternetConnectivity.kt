package com.bitlasoft.trackingo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bitlasoft.trackingo.activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InternetConnectivity : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {


        CoroutineScope(Dispatchers.Main).launch {
            MainActivity.isInternet.emit(isInternetAvailable(context))
        }

    }
}