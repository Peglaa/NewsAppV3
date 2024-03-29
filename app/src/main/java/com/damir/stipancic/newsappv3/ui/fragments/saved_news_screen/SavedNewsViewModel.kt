package com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.repository.NewsRepository
import kotlinx.coroutines.launch

class SavedNewsViewModel(private val repository: NewsRepository): ViewModel() {

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
        getSavedArticles()
    }

    fun getSavedArticles() = repository.getSavedArticles()

    fun saveArticle(article: Article) = viewModelScope.launch {
        article.id?.let { repository.saveArticle(it) }
    }

    fun unSaveArticle(article: Article) = viewModelScope.launch {
        article.id?.let { repository.unSaveArticle(it) }
    }
}