package com.damir.stipancic.newsappv3.ui.fragments.latest_news_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.stipancic.newsappv3.network.Article
import com.damir.stipancic.newsappv3.network.NewsApi
import kotlinx.coroutines.launch

enum class NewsApiStatus {ERROR, SUCCESSFUL, LOADING}

class LatestNewsViewModel : ViewModel(){

    // INTERNAL/EXTERNAL variables to track api status for UI changes
    private val _apiStatus = MutableLiveData<NewsApiStatus>()
    val apiStatus: LiveData<NewsApiStatus>
        get() = _apiStatus

    //INTERNAL/EXTERNAL variables to store list of news
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
        viewModelScope.launch {
            _apiStatus.value = NewsApiStatus.LOADING
            val newsResponse = NewsApi.retrofitService.getNews()
            if(newsResponse.isSuccessful){
                newsResponse.body()?.let {
                    _apiStatus.value = NewsApiStatus.SUCCESSFUL
                    _articles.value = it.articles
                }
            }
            else {
                _apiStatus.value = NewsApiStatus.ERROR
                _articles.value = ArrayList()
            }

        }
    }
}