package com.bitlasoft.trackingo.viewmodel

import androidx.lifecycle.ViewModel
import com.bitlasoft.trackingo.utils.sharedPref.PreferenceUtil

class MapTrackingoViewModel : ViewModel() {

    private var dialogShown: Boolean = false

    fun isDialogShown(): Boolean {
        return dialogShown
    }

    fun setDialogShown() {
        dialogShown = true
    }
}