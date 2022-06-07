package com.example.cryptotracker.api

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

sealed class DataState<T>

data class Success<T>(val data: T?) : DataState<T>()

data class Error<T>(val error: Exception, val data: T? = null) : DataState<T>()

class Loading<T> : DataState<T>()
