package com.example.newsfeed.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsfeed.R
import com.example.newsfeed.databinding.ActivityDetailsBinding
import com.example.newsfeed.databinding.ActivityMainBinding
import com.example.newsfeed.network.model.Result

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra(DETAILS_EXTRA)) {
            val data = intent.getSerializableExtra(DETAILS_EXTRA) as Result
            binding.title.text = data.webTitle
            binding.category.text = data.sectionName
            binding.webView.loadUrl(data.webUrl)
        }
    }
    companion object{
        const val DETAILS_EXTRA = "details_extra"
    }
}