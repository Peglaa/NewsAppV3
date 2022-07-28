package com.damir.stipancic.newsappv3.ui.fragments.search_news_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.data.models.NewsResponse
import com.damir.stipancic.newsappv3.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchNewsViewModel(private val repository : NewsRepository): ViewModel() {

    private val _searchResponse = MutableLiveData<Response<NewsResponse>>()
    val searchResponse: LiveData<Response<NewsResponse>>
        get() = _searchResponse

    //INTERNAL/EXTERNAL variables to track onClick navigation
    private val _navigateToClickedArticle = MutableLiveData<Article?>()
    val navigateToClickedArticle : LiveData<Article?>
        get() = _navigateToClickedArticle

    fun displayArticleDetails(article: Article) {
        _navigateToClickedArticle.value = article
    }

    fun displayArticleDetailsComplete() {
        _navigateToClickedArticle.value = null
    }

    fun getSearchedNews(searchQuery: String) = viewModelScope.launch {
        val response = repository.getSearchedNews(searchQuery, 1)
        if(response.isSuccessful)
            _searchResponse.value = response
    }
}