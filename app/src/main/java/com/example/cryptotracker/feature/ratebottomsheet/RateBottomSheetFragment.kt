package com.example.cryptotracker.feature.ratebottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cryptotracker.R
import com.example.cryptotracker.base.view.BaseBottomSheet
import com.example.cryptotracker.common.Variables
import com.example.cryptotracker.common.extension.toCurrency
import com.example.cryptotracker.common.extension.toRoundFourDigit
import com.example.cryptotracker.databinding.RateBottomSheetFragmentBinding
import com.example.cryptotracker.feature.ratebottomsheet.model.CoinRangeViewEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.slider.LabelFormatter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@AndroidEntryPoint
class RateBottomSheetFragment :
    BaseBottomSheet<RateBottomSheetViewModel, RateBottomSheetFragmentBinding>() {
    private val args: RateBottomSheetFragmentArgs by navArgs()

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.rateBottomSheetData = args.rateBottomSheetData
        mBottomSheetBehavior = BottomSheetBehavior.from(binding.rootContainer)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        return binding.root
    }

    override fun bindScreen() {
        binding.apply {
            viewModel.rateBottomSheetData.coinItem.apply {
                currentRates.text = coinCurrency.rates.toFloat().toCurrency()
                coinsName.text = name.replaceFirstChar { it.uppercaseChar() }
            }

            val currentRate = viewModel.rateBottomSheetData.coinItem.coinCurrency.rates.toFloat()
            rangeSlider.addOnChangeListener { rangeSlider, _, _ ->
                val values = rangeSlider.values
                viewModel.rangeOfCoin.minRate = values[0].toRoundFourDigit()
                viewModel.rangeOfCoin.maxRate = values[1].toRoundFourDigit()

                binding.minRangeLabel.text = values[0].toRoundFourDigit().toCurrency()
                binding.maxRangeLabel.text = values[1].toRoundFourDigit().toCurrency()
                btnSaveAndTrack.isEnabled = true
            }

            rangeSlider.valueFrom = 0F
            rangeSlider.valueTo = (currentRate * 2).toRoundFourDigit()
            rangeSlider.setValues(
                (currentRate * 3 / 4).toRoundFourDigit(),
                (currentRate * 4 / 3).toRoundFourDigit()
            )
            rangeSlider.labelBehavior = LabelFormatter.LABEL_GONE

            btnSaveAndTrack.setOnClickListener {
                btnSaveAndTrack.isEnabled = false
                viewModel.saveRangeOfCoin()
            }

            btnCoinsHistory.setOnClickListener {
                val action =
                    RateBottomSheetFragmentDirections.actionRateToHistory(
                        viewModel.rangeOfCoin.cointId.replaceFirstChar { it.uppercaseChar() } +
                            Variables.SPACE_CHAR + getString(R.string.label_history),
                        viewModel.rangeOfCoin.cointId
                    )
                findNavController().navigate(action)
            }
            viewModel.getRangeOfCoin()
        }
    }

    override fun observeViewModel() {
        viewModel.liveData.observe(this) {
            when (it) {
                is CoinRangeViewEntity -> {
                    binding.rangeSlider.setValues(
                        it.rangeItem.minRate?.toRoundFourDigit(),
                        it.rangeItem.maxRate?.toRoundFourDigit()
                    )
                }
            }
        }
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        RateBottomSheetFragmentBinding.inflate(LayoutInflater.from(requireContext()))

    override fun getViewModelClass() = RateBottomSheetViewModel::class.java

    override fun setRootContainer() = binding.rootContainer
}
