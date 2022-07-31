package com.damir.stipancic.newsappv3.ui.fragments.latest_news_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.repository.NewsRepository
import kotlinx.coroutines.launch

enum class NewsApiStatus {ERROR, SUCCESSFUL, LOADING}

class LatestNewsViewModel(private val repository : NewsRepository) : ViewModel(){

    // INTERNAL/EXTERNAL variables to track api status for UI changes
    private val _apiStatus = MutableLiveData<NewsApiStatus>()
    val apiStatus: LiveData<NewsApiStatus>
        get() = _apiStatus

    private val _isDBEmpty = MutableLiveData<Boolean>()
    val isDBEmpty: LiveData<Boolean>
    get() = _isDBEmpty

    //INTERNAL/EXTERNAL variables to track onClick navigation
    private var _navigateToClickedArticle = MutableLiveData<MutableList<Pair<List<Article>, Int>>?>()
    val navigateToClickedArticle : LiveData<MutableList<Pair<List<Article>, Int>>?>
        get() = _navigateToClickedArticle

    fun displayArticleDetails(pair: MutableList<Pair<List<Article>, Int>>) {
        _navigateToClickedArticle.value = pair
    }

    fun displayArticleDetailsComplete() {
        _navigateToClickedArticle.value = null
    }

    init {
        getLatestNews()
    }

    private fun getLatestNews() {
        _apiStatus.value = NewsApiStatus.LOADING
        viewModelScope.launch {
            try{
                repository.refreshNews()
                _apiStatus.value = NewsApiStatus.SUCCESSFUL
                _isDBEmpty.value = false
            }
            catch(e: Exception) {
                _apiStatus.value = NewsApiStatus.ERROR
                Log.d("latestNewsViewModel", "getLatestNews: ${e.message}")
            }
            if(repository.getArticlesFromDB().value?.isEmpty() == true)
                _isDBEmpty.value = true
        }
    }

    fun getLatestNewsFromDB() = repository.getArticlesFromDB()
}