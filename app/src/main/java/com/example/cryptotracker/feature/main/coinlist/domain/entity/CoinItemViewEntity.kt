package com.example.cryptotracker.feature.main.coinlist.domain.entity

import com.example.cryptotracker.base.data.model.BaseViewEntity
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinItem
import kotlinx.parcelize.Parcelize

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Parcelize
data class CoinItemViewEntity(
    val coinItem: List<CoinItem>
) : BaseViewEntity()
