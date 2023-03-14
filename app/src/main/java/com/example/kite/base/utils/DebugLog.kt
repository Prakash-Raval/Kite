package com.example.kite.base.utils

import android.util.Log

object DebugLog {
    private var className: String? = null
    private var lineNumber: Int = 0
    private var methodName: String? = null
    val DO_SOP = true

    private fun isDebuggable(): Boolean {
        return DO_SOP
    }

    private fun createLog(log: String): String {
        return "[" +
                methodName +
                ":" +
                lineNumber +
                "]" +
                log
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun e(message: String) {
        if (isDebuggable()) {
            getMethodNames(Throwable().stackTrace)
            Log.e(className, createLog(message))
        }
    }

    fun d(message: String) {
        if (isDebuggable()) {
            getMethodNames(Throwable().stackTrace)
            Log.d(className, createLog(message))
        }
    }

    fun print(e: Exception) {
        if (isDebuggable()) {
            e.printStackTrace()
        }
    }
}