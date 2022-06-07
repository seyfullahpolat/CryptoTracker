package com.example.cryptotracker.feature.main.coinhistory.data.repository

import com.example.cryptotracker.base.data.repository.BaseRepository
import com.example.cryptotracker.db.CoinHistoryItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */
abstract class CoinHistoryRepository : BaseRepository {
    // Local
    abstract suspend fun getAllHistoryById(id: String): Flow<List<CoinHistoryItem>>
}
