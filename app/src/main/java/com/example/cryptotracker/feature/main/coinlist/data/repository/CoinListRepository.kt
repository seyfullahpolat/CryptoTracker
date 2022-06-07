package com.example.cryptotracker.feature.main.coinlist.data.repository

import com.example.cryptotracker.api.DataState
import com.example.cryptotracker.base.data.repository.BaseRepository
import com.example.cryptotracker.db.CoinRangeItem
import com.example.cryptotracker.feature.main.coinlist.data.request.CoinListRequest
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

abstract class CoinListRepository : BaseRepository {
    // Remote data
    abstract suspend fun getCoinList(resquest: CoinListRequest): Flow<DataState<HashMap<String, HashMap<String, String>>>>

    // Local data
    abstract suspend fun getAllCoinRange(): Flow<List<CoinRangeItem>>
    abstract suspend fun updateCoinRangeEnable(item: CoinItem)
}
