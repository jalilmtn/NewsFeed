package com.example.newsfeed.ui.main

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.newsfeed.db.DatabaseHelper
import com.example.newsfeed.db.Resource
import com.example.newsfeed.network.MainRepository
import com.example.newsfeed.network.PassengersDataSource
import com.example.newsfeed.network.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PageViewModel constructor(
    private val repository: MainRepository,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val _index = MutableLiveData<Int>()
    private val _favNews = MutableLiveData<Resource<List<Result>>>()
    private val _errorMessage = MutableLiveData<String>()
    val favNews :LiveData<Resource<List<Result>>> = _favNews
    val errorMessage: LiveData<String> = _errorMessage

    val newsList =
        Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 30),
            pagingSourceFactory = {
                PassengersDataSource(repository , dbHelper)
            }).liveData.cachedIn(viewModelScope)


    private val searchClicked = Observer<Int> {
        when (it) {
            1 -> {}
            2 -> {}
            else -> throw IllegalStateException("Index is not valid")
        }
    }

    init {
        _index.observeForever(searchClicked)
        fetchNews()
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getIndex():Int? {
        return _index.value
    }


    fun addOrRemoveFavNews(item: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            if (item.isFav)
                dbHelper.insert(item)
            else
                dbHelper.delete(item)
        }
    }

    override fun onCleared() {
        _index.removeObserver(searchClicked)
        super.onCleared()
    }

    private fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _favNews.postValue(Resource.loading(null))
            dbHelper.getNews().collectLatest {
                _favNews.postValue(Resource.success(it))
            }
        }
    }

}