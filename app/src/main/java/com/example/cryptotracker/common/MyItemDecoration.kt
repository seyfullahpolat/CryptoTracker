package com.example.cryptotracker.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.common.extension.dpToPx

/**
 * Created by Seyfullah POLAT on 5.06.2022.
 * Copyright (c) 2022 Seyfullah POLAT. All rights reserved.
 */

class MyItemDecoration(val context: Context, space: Int = 8) : RecyclerView.ItemDecoration() {

    private val spaceInDp = space.dpToPx(context)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spaceInDp
        }
        outRect.bottom = spaceInDp
    }
}
