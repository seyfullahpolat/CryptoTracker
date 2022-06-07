package com.example.cryptotracker.feature.main.coinhistory.domain.interactor

import com.example.cryptotracker.db.CoinHistoryItem
import com.example.cryptotracker.feature.main.coinhistory.data.repository.CoinHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

class CoinHistoryLocalData @Inject constructor(
    private val repository: CoinHistoryRepository
) {
    suspend fun getAllHistoryById(id: String): Flow<List<CoinHistoryItem>> {
        return repository.getAllHistoryById(id)
    }
}
