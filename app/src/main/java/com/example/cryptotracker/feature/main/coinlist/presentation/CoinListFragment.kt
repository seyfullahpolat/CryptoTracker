package com.example.cryptotracker.feature.main.coinlist.presentation

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.cryptotracker.base.view.BaseFragment
import com.example.cryptotracker.common.extension.startTracking
import com.example.cryptotracker.databinding.FragmentCoinListBinding
import com.example.cryptotracker.feature.main.coinlist.domain.entity.CoinItemViewEntity
import com.example.cryptotracker.feature.ratebottomsheet.RateBottomSheetData
import com.example.cryptotracker.feature.ratebottomsheet.RateBottomSheetFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CoinListFragment : BaseFragment<CoinListViewModel, FragmentCoinListBinding>() {
    private val coinListAdapter: CoinListAdapter by lazy {
        CoinListAdapter()
    }

    override fun bindScreen() {
        binding.apply {
            coinList.adapter = coinListAdapter
            // coinList.addItemDecoration(MyItemDecoration(requireContext()))
        }

        coinListAdapter.showCoinDetail = { item ->
            val action =
                RateBottomSheetFragmentDirections.showRateBottomSheet(RateBottomSheetData(item))
            findNavController().navigate(action)
        }
        coinListAdapter.trackingStateListener = { item ->
            viewModel.saveRangeOfCoin(item)
        }

        viewModel.getCoinList()
    }

    override fun clickListeners() {}

    override fun observeViewModel() {
        viewModel.liveData.observe(this) { viewEntity ->
            when (viewEntity) {
                is CoinItemViewEntity -> {
                    checkTracking(viewEntity.coinItem.any { it.isTracking ?: false })
                    coinListAdapter.submitList(viewEntity.coinItem.map { it.copy() })
                    binding.emptyListMessage.isVisible = viewEntity.coinItem.isEmpty()
                }
            }
        }
    }

    private fun checkTracking(shouldBeStart: Boolean) {
        if (shouldBeStart) {
            requireContext().startTracking()
        } else {
            viewModel.workManager.cancelAllWork()
        }
    }

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentCoinListBinding =
        FragmentCoinListBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<CoinListViewModel> = CoinListViewModel::class.java
}
