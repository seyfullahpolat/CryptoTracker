package com.example.cryptotracker.base.data.repository

import com.example.cryptotracker.api.DataState
import com.example.cryptotracker.api.Error
import com.example.cryptotracker.api.Success
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

interface BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): DataState<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return Success(data = body)
                }
            }
            return error(HttpException(response))
        } catch (e: Exception) {
            return error(e)
        }
    }

    private fun <T> error(errorMessage: Exception): DataState<T> = Error(errorMessage)
}
