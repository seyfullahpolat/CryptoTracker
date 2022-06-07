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

@Entity(tableName = "CoinRangeItem")
data class CoinRangeItem(
    @PrimaryKey
    @ColumnInfo(name = "coin_id")
    val cointId: String,

    @ColumnInfo(name = "min_rate")
    var minRate: Float?,

    @ColumnInfo(name = "max_rate")
    var maxRate: Float?,

    @ColumnInfo(name = "enable")
    var enable: Boolean = false,

    var createdAt: Date? = Calendar.getInstance().time,

    var updatedAt: Date? = Calendar.getInstance().time
)
