package com.example.cryptotracker.feature.main.coinhistory.presentation

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.cryptotracker.base.view.BaseFragment
import com.example.cryptotracker.common.MyItemDecoration
import com.example.cryptotracker.databinding.FragmentCoinListBinding
import com.example.cryptotracker.feature.main.coinhistory.domain.entity.CoinHistoryViewEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CoinHistoryListFragment : BaseFragment<CoinHistoryViewModel, FragmentCoinListBinding>() {
    private val args: CoinHistoryListFragmentArgs by navArgs()

    private val coinHistoryAdapter: CoinHistoryAdapter by lazy {
        CoinHistoryAdapter()
    }

    override fun bindScreen() {
        viewModel.coinId = args.coinId
        binding.apply {
            coinList.adapter = coinHistoryAdapter
            coinList.addItemDecoration(MyItemDecoration(requireContext()))
        }

        viewModel.getHistory()
    }

    override fun clickListeners() {}

    override fun observeViewModel() {
        viewModel.liveData.observe(this) { viewEntity ->
            when (viewEntity) {
                is CoinHistoryViewEntity -> {
                    binding.emptyListMessage.isVisible = viewEntity.historyList.isEmpty()
                    coinHistoryAdapter.submitList(viewEntity.historyList.map { it.copy() })
                }
            }
        }
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        FragmentCoinListBinding.inflate(layoutInflater)

    override fun getViewModelClass() = CoinHistoryViewModel::class.java
}
