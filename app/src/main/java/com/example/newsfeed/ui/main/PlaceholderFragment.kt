package com.example.newsfeed.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.newsfeed.databinding.FragmentMainBinding
import com.example.newsfeed.db.DatabaseBuilder
import com.example.newsfeed.db.DatabaseHelperImpl
import com.example.newsfeed.db.Status
import com.example.newsfeed.network.MainRepository
import com.example.newsfeed.network.RetrofitService
import com.example.newsfeed.network.model.Result
import com.example.newsfeed.ui.details.DetailsActivity
import com.example.newsfeed.utils.CheckChangeListener
import com.example.newsfeed.utils.MyViewModelFactory
import kotlinx.coroutines.launch


class PlaceholderFragment : Fragment(), CheckChangeListener {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null
    private val retrofitService = RetrofitService.getInstance()
    private val newsAdapter = NewsAdapter()
    private val favoriteAdapter = FavoriteAdapter(arrayListOf())
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsAdapter.checkChangeListener = this
        favoriteAdapter.checkChangeListener = this
        pageViewModel = ViewModelProvider(
            this,
            MyViewModelFactory(
                MainRepository(retrofitService),
                DatabaseHelperImpl(appDatabase = DatabaseBuilder.getInstance(requireContext()))
            )
        )[PageViewModel::class.java].apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root
        if (pageViewModel.getIndex() == 1) {
            binding.recyclerview.adapter = newsAdapter
            pageViewModel.newsList.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    newsAdapter.submitData(pagingData = it)
                }
            }
        } else {
            binding.recyclerview.adapter = favoriteAdapter
        }
        pageViewModel.favNews.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (pageViewModel.getIndex() == 1) {
                        newsAdapter.snapshot().items.forEach { items ->
                            items.isFav =
                                items.id == it.data?.firstOrNull { favItem -> favItem.id == items.id }?.id
                        }
                        newsAdapter.notifyDataSetChanged()
                    } else
                        it.data?.let { it1 -> favoriteAdapter.addData(it1) }
                }
                else -> {
                    //TODO, handle load and error state here
                }
            }

        }
        pageViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCheckChange(item: Result, position: Int) {
        pageViewModel.addOrRemoveFavNews(item)
    }

    override fun onClick(item: Result, view: View) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.DETAILS_EXTRA, item);
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(), view, "itemView"
        )
        startActivity(intent, options.toBundle())
    }
}