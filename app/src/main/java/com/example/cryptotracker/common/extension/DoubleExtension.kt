package com.example.cryptotracker.common.extension

import com.example.cryptotracker.common.Variables
import java.text.DecimalFormat

/**
 * Created by Seyfullah POLAT on 4.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

fun Float.toCurrency(): String {
    val formatter = DecimalFormat(Variables.DECIMAL_FORMAT)
    return formatter.format(this) + Variables.SPACE_CHAR + Variables.CURRENT_CURRENCY
}
