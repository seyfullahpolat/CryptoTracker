package com.example.cryptotracker.feature.main.coinhistory.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.common.extension.toReadableDate
import com.example.cryptotracker.databinding.ItemCoinRangeHistoryListBinding
import com.example.cryptotracker.db.CoinHistoryItem

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

class CoinHistoryAdapter : ListAdapter<CoinHistoryItem, CoinHistoryAdapter.ViewHolder>(notify) {
    companion object {
        val notify = object : DiffUtil.ItemCallback<CoinHistoryItem>() {
            override fun areItemsTheSame(
                oldItem: CoinHistoryItem,
                newItem: CoinHistoryItem
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CoinHistoryItem,
                newItem: CoinHistoryItem
            ) = oldItem.coinId == newItem.coinId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCoinRangeHistoryListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemCoinRangeHistoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CoinHistoryItem) {
            binding.apply {
                logDate.text = item.createdAt?.toReadableDate()
                coinPrice.text = "${item.coinId} ${item.coinPrice}"
            }
        }
    }
}
