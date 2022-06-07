package com.example.cryptotracker.feature.main.coinlist.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.cryptotracker.api.Success
import com.example.cryptotracker.base.view.BaseViewModel
import com.example.cryptotracker.common.Variables
import com.example.cryptotracker.common.extension.safeCollect
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinCurrency
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinItem
import com.example.cryptotracker.feature.main.coinlist.domain.entity.CoinItemViewEntity
import com.example.cryptotracker.feature.main.coinlist.domain.interactor.CoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val coinListUseCase: CoinListUseCase,
    val workManager: WorkManager
) : BaseViewModel(), LifecycleObserver {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var responseList: List<CoinItem> = emptyList()

    @ExperimentalCoroutinesApi
    fun getCoinList() {
        val param = CoinListUseCase.Params(
            ids = "bitcoin,ethereum,ripple",
            vs_currencies = Variables.CURRENT_CURRENCY
        )
        viewModelScope.launch(dispatcher) {
            coinListUseCase.execute(param).collect { dataState ->
                loading.value = dataState
                if (dataState is Success) dataState.data?.apply {
                    responseList = dataState.data.map { coinItem ->
                        CoinItem(
                            coinItem.key,
                            coinItem.value.map {
                                CoinCurrency(
                                    it.key,it.value
                                )
                            }.first()
                        )
                    }
                }
            }

            coinListUseCase.repository.getAllCoinRange().safeCollect { list ->
                responseList.map { responseItem ->
                    responseItem.isTracking = list.find {
                        it.cointId == responseItem.name
                    }?.enable
                }
                _liveData.postValue(
                    CoinItemViewEntity(
                        responseList
                    )
                )
            }
        }
    }

    fun saveRangeOfCoin(item: CoinItem) {
        viewModelScope.launch(dispatcher) {
            coinListUseCase.repository.updateCoinRangeEnable(item)
        }
    }
}
