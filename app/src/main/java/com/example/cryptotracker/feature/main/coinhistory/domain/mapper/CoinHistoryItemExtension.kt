package com.example.cryptotracker.feature.main.coinhistory.domain.mapper

import com.example.cryptotracker.db.CoinHistoryItem
import com.example.cryptotracker.feature.main.coinhistory.domain.entity.CoinHistoryViewEntity

fun List<CoinHistoryItem>.toViewEntity() = CoinHistoryViewEntity(
    historyList = this
)
