package com.example.cryptotracker.feature.main.coinhistory.data.repository

import com.example.cryptotracker.db.CoinHistoryItem
import com.example.cryptotracker.db.CryptoRoomDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Singleton
class CoinHistoryRepositoryImpl @Inject constructor(
    private val cryptoDb: CryptoRoomDatabase
) : CoinHistoryRepository() {

    override suspend fun getAllHistoryById(id: String): Flow<List<CoinHistoryItem>> {
        return cryptoDb.coinHistoryDao().getCoinHistoryById(id)
    }
}
