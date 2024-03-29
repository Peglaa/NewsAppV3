package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damir.stipancic.newsappv3.repository.NewsRepository

class ArticleDetailsViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArticleDetailViewModel::class.java)) {
                return ArticleDetailViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}