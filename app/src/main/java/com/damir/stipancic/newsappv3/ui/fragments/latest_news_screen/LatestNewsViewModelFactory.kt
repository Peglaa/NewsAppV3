package com.damir.stipancic.newsappv3.ui.fragments.latest_news_screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen.ArticleDetailViewModel

class LatestNewsViewModelFactory(
    private val context: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LatestNewsViewModel::class.java)) {
            return LatestNewsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}