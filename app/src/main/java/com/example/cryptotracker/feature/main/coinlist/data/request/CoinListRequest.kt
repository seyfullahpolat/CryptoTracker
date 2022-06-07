package com.example.cryptotracker.feature.main.coinlist.data.request

import com.example.cryptotracker.base.data.model.BaseRequest

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

data class CoinListRequest(
    val ids: String,
    val vs_currencies: String
) : BaseRequest()
