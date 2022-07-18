package com.example.newsfeed.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.databinding.AdapterNewsBinding
import com.example.newsfeed.network.model.Result
import com.example.newsfeed.utils.CheckChangeListener

class FavoriteAdapter(private val dataSet: ArrayList<Result>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    lateinit var checkChangeListener: CheckChangeListener

    @SuppressLint("NotifyDataSetChanged")
    fun addData(dataSet: List<Result>) {
        this.dataSet.clear()
        this.dataSet.addAll(dataSet)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            AdapterNewsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return FavoriteViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size
    inner class FavoriteViewHolder(val binding: AdapterNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        with(holder.binding) {
            with(dataSet[position]) {

                title.text = webTitle
                category.text = sectionName
                star.isChecked = isFav
                star.setOnClickListener {
                    isFav = !isFav
                    checkChangeListener.onCheckChange(this, position)
                }
                cvMain.setOnClickListener {
                    checkChangeListener.onClick(this, cvMain)
                }
            }
        }

    }
}
