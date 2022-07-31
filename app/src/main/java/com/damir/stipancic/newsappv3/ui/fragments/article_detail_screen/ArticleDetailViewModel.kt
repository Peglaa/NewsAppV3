package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.repository.NewsRepository
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val repository : NewsRepository) : ViewModel() {

    fun onSaveClicked(article: Article) = viewModelScope.launch {
        article.id?.let { repository.saveArticle(it) }
    }


    fun insertArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }
}