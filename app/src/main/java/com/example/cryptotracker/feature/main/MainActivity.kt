package com.example.cryptotracker.feature.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.cryptotracker.R
import com.example.cryptotracker.base.view.BaseActivity
import com.example.cryptotracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override fun bindScreen() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.coinListFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
            navController,
            appBarConfiguration
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            startActivity(
                Intent(
                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                    Uri.parse(
                        "package:$packageName"
                    )
                )
            )
        }

        /**  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         val intent = Intent()
         val packageName = packageName
         val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
         if (!pm.isIgnoringBatteryOptimizations(packageName)) {
         intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
         intent.data = Uri.parse("package:$packageName")
         startActivity(intent)
         }
         }*/
    }

    override fun clickListeners() {}

    override fun observeViewModel() {}

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)
}
