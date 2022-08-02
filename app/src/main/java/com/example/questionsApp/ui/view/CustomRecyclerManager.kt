package com.example.questionsApp.ui.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

open class CustomRecyclerManager(context: Context?) : LinearLayoutManager(context) {
    private var isScrollEnabled = true

    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }
}