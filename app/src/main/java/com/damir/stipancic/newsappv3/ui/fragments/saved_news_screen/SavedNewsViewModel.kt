package com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.repository.NewsRepository

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
}