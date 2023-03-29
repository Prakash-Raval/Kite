package com.example.kite.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

suspend fun <T> retrying(
    fallbackValue: T?,
    tryCnt: Int,
    intervalMillis: (attempt: Int) -> Long,
    retryCheck: (Throwable) -> Boolean,
    block: suspend () -> T,
): T {
    try {
        val retryCnt = tryCnt - 1
        repeat(retryCnt) { attempt ->
            try {
                return block()
            } catch (e: Exception) {
                if (e is CancellationException || !retryCheck(e)) {
                    throw e
                }
            }
            delay(intervalMillis(attempt + 1))
        }
        return block()
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        return fallbackValue ?: throw e
    }
}


