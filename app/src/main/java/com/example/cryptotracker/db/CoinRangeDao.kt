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
interface CoinRangeDao {

    @Query("SELECT * FROM CoinRangeItem WHERE coin_id = :coin_id")
    fun getCoinRangeById(coin_id: String): Flow<CoinRangeItem?>

    @Query("SELECT * FROM CoinRangeItem")
    fun getAllCoinRange(): Flow<List<CoinRangeItem>>

    @Query("SELECT * FROM CoinRangeItem WHERE enable = 1")
    fun getCoinRangeEnable(): Flow<List<CoinRangeItem>>

    @Query("SELECT coin_id FROM CoinRangeItem WHERE coin_id = :coin_id and enable = 1 and :rate NOT BETWEEN min_rate and max_rate")
    fun getRateOutRangeCoinById(rate: Float, coin_id: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCoinRange(coinRangeItem: CoinRangeItem)

    @Query("UPDATE CoinRangeItem SET enable = :isTracking Where coin_id = :id")
    suspend fun updateCoinTrackingState(id: String, isTracking: Boolean)
}
