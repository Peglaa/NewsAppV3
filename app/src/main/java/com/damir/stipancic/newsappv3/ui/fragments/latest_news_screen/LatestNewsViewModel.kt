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

    //INTERNAL/EXTERNAL variables to store list of news articles
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    //INTERNAL/EXTERNAL variables to track onClick navigation
    private val _navigateToClickedArticle = MutableLiveData<Article>()
    val navigateToClickedArticle : LiveData<Article>
        get() = _navigateToClickedArticle

    fun displayArticleDetails(article: Article) {
        _navigateToClickedArticle.value = article
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
                _articles.value = repository.getArticlesFromDB()
                _apiStatus.value = NewsApiStatus.SUCCESSFUL
            }
            catch(e: Exception) {
                _apiStatus.value = NewsApiStatus.ERROR
                _articles.value = ArrayList()
                Log.d("latestNewsViewModel", "getLatestNews: ${e.message}")
            }
        }
    }

    fun updateRecyclerItems(){
        viewModelScope.launch {
            _articles.value = repository.getArticlesFromDB()
        }
    }
}