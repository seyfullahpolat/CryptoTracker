package com.example.cryptotracker.feature.ratebottomsheet

import android.os.Parcelable
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinItem
import kotlinx.parcelize.Parcelize

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Parcelize
data class RateBottomSheetData(
    val coinItem: CoinItem
) : Parcelable
