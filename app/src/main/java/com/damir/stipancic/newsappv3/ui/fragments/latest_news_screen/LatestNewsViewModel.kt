package com.damir.stipancic.newsappv3.ui.fragments.latest_news_screen

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.data.network.NewsApi
import kotlinx.coroutines.launch

enum class NewsApiStatus {ERROR, SUCCESSFUL, LOADING}

class LatestNewsViewModel(val context : Application) : ViewModel(){

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
        val database = ArticleDatabase.getInstance(context)
        viewModelScope.launch {
            try{
            _apiStatus.value = NewsApiStatus.LOADING
            val newsResponse = NewsApi.retrofitService.getNews()
                newsResponse.body()?.let { response ->
                    _apiStatus.value = NewsApiStatus.SUCCESSFUL
                    _articles.value = response.articles
                    Log.d("latestNewsViewModel", "getLatestNews: SUCCESSFUL")
                    _articles.value.let{ articleList ->
                        articleList?.forEach{
                            database.sleepDatabaseDao.insertArticle(it)
                        }
                    }
                }
                Log.d("latestNewsViewModel", "UNSAVED_ROW_COUNT: ${database.sleepDatabaseDao.getUnsavedRowCount()}")
                if(database.sleepDatabaseDao.getUnsavedRowCount() >= 20)
                    database.sleepDatabaseDao.deleteNotNeededArticles()
                Log.d("latestNewsViewModel", "UNSAVED_ROW_COUNT_AFTER_DELETE: ${database.sleepDatabaseDao.getUnsavedRowCount()}")
                database.sleepDatabaseDao.getAllArticles().forEach{
                    Log.d("latestNewsViewModel", "TITLE: ${it.title}")
                }
            }
            catch(e: Exception) {
                _apiStatus.value = NewsApiStatus.ERROR
                _articles.value = ArrayList()
                Log.d("latestNewsViewModel", "getLatestNews: ${e.message}")
            }
        }
    }
}