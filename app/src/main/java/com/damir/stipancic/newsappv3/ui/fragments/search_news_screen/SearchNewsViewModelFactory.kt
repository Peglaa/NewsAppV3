package com.damir.stipancic.newsappv3.ui.fragments.search_news_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damir.stipancic.newsappv3.repository.NewsRepository

class SearchNewsViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchNewsViewModel::class.java)) {
            return SearchNewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}