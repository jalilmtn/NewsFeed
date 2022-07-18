package com.example.newsfeed.utils

import android.view.View
import com.example.newsfeed.network.model.Result

interface CheckChangeListener {
    fun onCheckChange(item: Result, position: Int)
    fun onClick(item: Result, view: View)
}