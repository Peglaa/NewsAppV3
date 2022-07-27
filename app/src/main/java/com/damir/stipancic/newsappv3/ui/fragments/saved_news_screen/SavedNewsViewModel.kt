package com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.repository.NewsRepository
import kotlinx.coroutines.launch

class SavedNewsViewModel(private val repository: NewsRepository): ViewModel() {

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
        getSavedArticles()
    }

    fun getSavedArticles() = repository.getSavedArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun insertArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }
}