package com.example.cryptotracker.base.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    abstract fun bindScreen()
    abstract fun clickListeners()
    abstract fun observeViewModel()
    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB
    protected abstract fun getViewModelClass(): Class<VM>

    lateinit var binding: VB
    internal val viewModel: VM by lazy { ViewModelProvider(this)[getViewModelClass()] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        bindScreen()
        clickListeners()
        observeViewModel()
        observer()
    }

    private fun observer() {
        viewModel.run {
            loading.observe(this@BaseActivity) {
                // todo etc. progressDialog(true)
            }
        }
    }
}
