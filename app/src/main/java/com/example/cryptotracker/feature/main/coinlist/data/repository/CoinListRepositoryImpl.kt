package com.example.cryptotracker.feature.main.coinlist.data.repository

import com.example.cryptotracker.api.ApiServices
import com.example.cryptotracker.api.DataState
import com.example.cryptotracker.api.Loading
import com.example.cryptotracker.db.CoinRangeItem
import com.example.cryptotracker.db.CryptoRoomDatabase
import com.example.cryptotracker.feature.main.coinlist.data.request.CoinListRequest
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Singleton
class CoinListRepositoryImpl @Inject constructor(
    private val remoteDataSource: ApiServices,
    private val db: CryptoRoomDatabase
) : CoinListRepository() {
    override suspend fun getCoinList(resquest: CoinListRequest): Flow<DataState<HashMap<String, HashMap<String, String>>>> {
        return flow {
            emit(Loading())
            emit(
                safeApiCall {
                    remoteDataSource.getCoinList(
                        resquest.ids,
                        resquest.vs_currencies
                    )
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllCoinRange(): Flow<List<CoinRangeItem>> {
        return db.coinRangeDao().getAllCoinRange()
    }

    override suspend fun updateCoinRangeEnable(item: CoinItem) {
        db.coinRangeDao().updateCoinTrackingState(item.name, item.isTracking ?: false)
    }
}
