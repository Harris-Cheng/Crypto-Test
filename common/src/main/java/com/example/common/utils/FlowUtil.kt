package com.example.common.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.(data: T) -> Unit
) = lifecycleOwner.lifecycleScope.launch {
    lifecycleOwner.repeatOnLifecycle(state) {
        collectLatest {
            block(it)
        }
    }
}

fun <T> Flow<T>.throttleLatest(delayMillis: Long): Flow<T> = this
    .conflate()
    .transform {
        emit(it)
        delay(delayMillis)
    }