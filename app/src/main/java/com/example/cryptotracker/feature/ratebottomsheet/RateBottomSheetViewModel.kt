package com.example.cryptotracker.feature.ratebottomsheet

import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.cryptotracker.base.view.BaseViewModel
import com.example.cryptotracker.db.CoinRangeItem
import com.example.cryptotracker.db.CryptoRoomDatabase
import com.example.cryptotracker.feature.ratebottomsheet.model.CoinRangeViewEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@HiltViewModel
class RateBottomSheetViewModel @Inject constructor(
    private val db: CryptoRoomDatabase,
    val workManager: WorkManager
) : BaseViewModel() {
    lateinit var rateBottomSheetData: RateBottomSheetData

    val rangeOfCoin: CoinRangeItem by lazy {
        val currentRate = rateBottomSheetData.coinItem.coinCurrency.rates.toFloat()
        CoinRangeItem(rateBottomSheetData.coinItem.name, currentRate, currentRate, false)
    }

    fun saveRangeOfCoin() {
        rangeOfCoin.enable = true
        viewModelScope.launch {
            db.coinRangeDao().updateCoinRange(rangeOfCoin)
        }
    }

    fun getRangeOfCoin() {
        rangeOfCoin.enable = true
        viewModelScope.launch {
            db.coinRangeDao().getCoinRangeById(rangeOfCoin.cointId).collect { rangeItem ->
                rangeItem?.let { _liveData.value = CoinRangeViewEntity(rangeItem = it) }
            }
        }
    }
}
