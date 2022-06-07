package com.example.cryptotracker.feature.main.coinlist.domain.interactor

import com.example.cryptotracker.api.DataState
import com.example.cryptotracker.base.interactor.BaseRequestUseCase
import com.example.cryptotracker.feature.main.coinlist.data.repository.CoinListRepository
import com.example.cryptotracker.feature.main.coinlist.data.request.CoinListRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

class CoinListUseCase @Inject constructor(
    val repository: CoinListRepository
) : BaseRequestUseCase<CoinListUseCase.Params, CoinListRequest, HashMap<String, HashMap<String, String>>>() {

    override suspend fun execute(params: Params): Flow<DataState<out HashMap<String, HashMap<String, String>>>> {
        return repository.getCoinList(onCreateRequest(params))
    }

    override fun onCreateRequest(params: Params): CoinListRequest = CoinListRequest(
        ids = params.ids,
        vs_currencies = params.vs_currencies
    )

    class Params(
        var ids: String,
        var vs_currencies: String
    )
}
