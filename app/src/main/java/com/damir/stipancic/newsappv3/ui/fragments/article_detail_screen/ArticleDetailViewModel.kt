package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damir.stipancic.newsappv3.data.models.Article

class ArticleDetailViewModel(article: Article) : ViewModel() {

    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article>
        get() = _selectedArticle

    init {
        _selectedArticle.value = article
    }

}