package com.damir.stipancic.newsappv3.ui.fragments.search_news_screen

import android.util.Log
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
    private var _navigateToClickedArticle = MutableLiveData<MutableList<Pair<List<Article>, Int>>?>()
    val navigateToClickedArticle : LiveData<MutableList<Pair<List<Article>, Int>>?>
        get() = _navigateToClickedArticle

    fun displayArticleDetails(pair: MutableList<Pair<List<Article>, Int>>) {
        _navigateToClickedArticle.value = pair
    }

    fun displayArticleDetailsComplete() {
        _navigateToClickedArticle.value = null
    }

    fun getSearchedNews(searchQuery: String) = viewModelScope.launch {
        try {
            val response = repository.getSearchedNews(searchQuery, 1)
            if(response.isSuccessful)
                _searchResponse.value = response
        }
        catch (e: Exception){
            Log.d("searchNewsViewModel", "getSearchedNews: NO INTERNET CONNECTION")
        }

    }
}