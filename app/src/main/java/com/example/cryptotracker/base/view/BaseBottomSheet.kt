package com.example.cryptotracker.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

abstract class BaseBottomSheet<VM : BaseViewModel, VB : ViewBinding> : BottomSheetDialogFragment() {
    abstract fun bindScreen()
    abstract fun observeViewModel()
    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB
    protected abstract fun getViewModelClass(): Class<VM>
    protected abstract fun setRootContainer(): View
    lateinit var binding: VB
    internal val viewModel: VM by lazy { ViewModelProvider(this)[getViewModelClass()] }

    private val viewObserver: ViewTreeObserver.OnGlobalLayoutListener by lazy {
        ViewTreeObserver.OnGlobalLayoutListener {
            mBottomSheetBehavior.peekHeight = setRootContainer().measuredHeight
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRootContainer().viewTreeObserver.addOnGlobalLayoutListener(viewObserver)
        mBottomSheetBehavior = BottomSheetBehavior.from(setRootContainer())
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        observeViewModel()
        bindScreen()
        setRootContainer().viewTreeObserver?.removeOnGlobalLayoutListener(viewObserver)
    }
}
