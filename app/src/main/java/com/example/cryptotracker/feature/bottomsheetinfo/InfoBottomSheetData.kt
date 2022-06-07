package com.example.cryptotracker.feature.bottomsheetinfo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

@Parcelize
data class InfoBottomSheetData(
    val title: String,
    val firstBtn: String? = null,
    val secondBtn: String? = null,
    val firstBtnAction: () -> Unit? = {},
    val secondBtnAction: () -> Unit? = {}
) : Parcelable
