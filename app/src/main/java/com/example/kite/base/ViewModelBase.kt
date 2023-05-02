package com.example.kite.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class ViewModelBase : ViewModel() {


    private val job = SupervisorJob()
    val coroutineContext = Dispatchers.Main + job
    var snackBarMessage = MutableLiveData<Any>()
    var progressBarDisplay = MutableLiveData(false)


    /**
     * Cancel the job when the view model is destroyed
     */
    override fun onCleared() {
        try {
            super.onCleared()
            coroutineContext.cancel()
        } catch (e: Exception) {
            Log.e("data", "data")
        }
    }

    fun getSnakeBarMessage(): MutableLiveData<Any> {
        return snackBarMessage
    }


    fun getProgressBar(): MutableLiveData<Boolean> {
        return progressBarDisplay
    }
    /**
     * This method is used to display the Snake Bar Message
     *
     * @param message string object to display.
     */
    fun showSnackBarMessage(message: Any) {
        snackBarMessage.value = message
    }


    fun showProgressBar(display: Boolean) {
        progressBarDisplay.value = display
    }



}