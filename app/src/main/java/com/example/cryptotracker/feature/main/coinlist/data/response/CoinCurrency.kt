package com.example.cryptotracker.feature.main.coinlist.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Parcelize
data class CoinCurrency(
    val currency: String,
    val rates: String
) : Parcelable
