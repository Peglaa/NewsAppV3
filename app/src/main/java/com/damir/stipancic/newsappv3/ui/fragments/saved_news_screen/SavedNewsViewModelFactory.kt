package com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damir.stipancic.newsappv3.repository.NewsRepository

class SavedNewsViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedNewsViewModel::class.java)) {
            return SavedNewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}