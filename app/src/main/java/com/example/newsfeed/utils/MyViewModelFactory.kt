package com.example.newsfeed.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsfeed.db.DatabaseHelper
import com.example.newsfeed.network.MainRepository
import com.example.newsfeed.ui.main.PageViewModel

class MyViewModelFactory constructor(
    private val repository: MainRepository,
    private val dbHelper: DatabaseHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PageViewModel::class.java)) {
            PageViewModel(this.repository, this.dbHelper) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}