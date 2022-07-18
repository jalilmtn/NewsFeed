package com.example.newsfeed.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.databinding.AdapterNewsBinding
import com.example.newsfeed.network.model.Result
import com.example.newsfeed.utils.CheckChangeListener

class NewsAdapter :
    PagingDataAdapter<Result, NewsAdapter.NewsViewHolder>(PassengersComparator) {
    lateinit var checkChangeListener: CheckChangeListener
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        return NewsViewHolder(
            AdapterNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPassenger(it, position) }
    }

    inner class NewsViewHolder(private val binding: AdapterNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindPassenger(item: Result, position: Int) = with(binding) {
            title.text = item.webTitle
            category.text = item.sectionName
            star.isChecked = item.isFav
            star.setOnClickListener {
                item.isFav = !item.isFav
                checkChangeListener.onCheckChange(item, position)
            }
            cvMain.setOnClickListener {
                checkChangeListener.onClick(item, cvMain)
            }
        }
    }

    object PassengersComparator : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }


}