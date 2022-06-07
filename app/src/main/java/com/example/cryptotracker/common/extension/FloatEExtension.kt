package com.example.cryptotracker.common.extension

import kotlin.math.roundToInt

/**
 * Created by Seyfullah POLAT on 6.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

class FloatEExtension

fun Float.toRoundFourDigit() = ((this * 10000.0).roundToInt() / 10000.0).toFloat()
