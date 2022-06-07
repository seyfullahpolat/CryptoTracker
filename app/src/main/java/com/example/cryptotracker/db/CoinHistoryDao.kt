package com.example.cryptotracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Dao
interface CoinHistoryDao {
    @Query("SELECT * FROM CoinHistoryItem WHERE coin_id = :id Order by createdAt desc")
    fun getCoinHistoryById(id: String): Flow<List<CoinHistoryItem>>

    /**
     * @Query("SELECT * FROM CoinHistoryItem WHERE coin_id = :id")
     *  fun getCoinHistoryById(id: String): Flow<List<CoinHistoryItem>>
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHistory(coinHistoryItem: CoinHistoryItem)
}
