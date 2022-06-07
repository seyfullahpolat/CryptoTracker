package com.example.cryptotracker.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

interface ApiServices {
    @GET("api/v3/simple/price")
    suspend fun getCoinList(
        @Query("ids") ids: String,
        @Query("vs_currencies") vs_currencies: String
    ): Response<HashMap<String, HashMap<String, String>>>
}
