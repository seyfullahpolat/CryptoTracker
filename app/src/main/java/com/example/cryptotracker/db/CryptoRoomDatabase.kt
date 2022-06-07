package com.example.cryptotracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Database(
    entities = [CoinHistoryItem::class, CoinRangeItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CryptoRoomDatabase : RoomDatabase() {
    abstract fun coinHistoryDao(): CoinHistoryDao
    abstract fun coinRangeDao(): CoinRangeDao
}
