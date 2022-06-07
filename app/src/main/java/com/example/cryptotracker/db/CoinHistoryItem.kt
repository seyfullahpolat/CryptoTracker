package com.example.cryptotracker.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Entity(tableName = "CoinHistoryItem")
data class CoinHistoryItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "coin_id")
    val coinId: String,

    @ColumnInfo(name = "coin_price")
    val coinPrice: String,

    var createdAt: Date? = Calendar.getInstance().time,

    var updatedAt: Date? = Calendar.getInstance().time
)
