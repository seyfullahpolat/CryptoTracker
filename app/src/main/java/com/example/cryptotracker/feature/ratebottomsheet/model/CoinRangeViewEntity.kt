package com.example.cryptotracker.feature.ratebottomsheet.model

import com.example.cryptotracker.base.data.model.BaseViewEntity
import com.example.cryptotracker.db.CoinRangeItem

/**
 * Created by Seyfullah POLAT on 7.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

data class CoinRangeViewEntity(
    val rangeItem: CoinRangeItem
) : BaseViewEntity()
