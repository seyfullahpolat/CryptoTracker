package com.example.cryptotracker.common.extension

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
    collect {
        currentCoroutineContext().ensureActive()
        action(it)
    }
}

fun <T> Flow<T>.merge(otherFlow: Flow<T>) = flow {
    this@merge.collect { value ->
        emit(value)
    }
    otherFlow.collect { value ->
        emit(value)
    }
}
