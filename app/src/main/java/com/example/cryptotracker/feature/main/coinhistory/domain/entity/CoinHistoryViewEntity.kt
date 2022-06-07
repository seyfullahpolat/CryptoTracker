package com.example.cryptotracker.feature.main.coinhistory.domain.entity

import com.example.cryptotracker.base.data.model.BaseViewEntity
import com.example.cryptotracker.db.CoinHistoryItem

data class CoinHistoryViewEntity(
    val historyList: List<CoinHistoryItem>
) : BaseViewEntity()
