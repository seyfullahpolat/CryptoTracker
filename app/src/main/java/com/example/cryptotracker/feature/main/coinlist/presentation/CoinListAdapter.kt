package com.example.cryptotracker.feature.main.coinlist.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.common.extension.toCurrency
import com.example.cryptotracker.databinding.ItemCoinListBinding
import com.example.cryptotracker.feature.main.coinlist.data.response.CoinItem

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

class CoinListAdapter :
    ListAdapter<CoinItem, CoinListAdapter.ViewHolder>(notify) {
    companion object {
        val notify = object : DiffUtil.ItemCallback<CoinItem>() {
            override fun areItemsTheSame(
                oldItem: CoinItem,
                newItem: CoinItem
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: CoinItem,
                newItem: CoinItem
            ) = oldItem.name == newItem.name && oldItem.isTracking == newItem.isTracking
        }
    }

    var showCoinDetail: (coinItem: CoinItem) -> Unit = { }
    var trackingStateListener: (coinItem: CoinItem) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCoinListBinding.inflate(
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

    inner class ViewHolder(private val binding: ItemCoinListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CoinItem) {
            binding.apply {
                coinName.text = item.name.replaceFirstChar { it.uppercaseChar() }
                item.coinCurrency.apply {
                    coinPrice.text = rates.toFloat().toCurrency()
                }

                coinTrackingStatus.setOnCheckedChangeListener(null)
                coinTrackingStatus.isEnabled = item.isTracking != null
                coinTrackingStatus.isChecked = item.isTracking ?: false
                coinTrackingStatus.setOnCheckedChangeListener { _, isChecked ->
                    item.isTracking = isChecked
                    trackingStateListener.invoke(item)
                }
            }
            itemView.setOnClickListener {
                showCoinDetail(item)
            }
        }
    }
}
