package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.repository.NewsRepository
import kotlinx.coroutines.launch

class ArticleDetailViewModel(article: Article, private val repository : NewsRepository) : ViewModel() {

    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article>
        get() = _selectedArticle

    private val _updateRecyclerOnBack = MutableLiveData<Boolean>()
    val updateRecyclerOnBack: LiveData<Boolean>
        get() = _updateRecyclerOnBack

    init {
        _updateRecyclerOnBack.value = false
        _selectedArticle.value = article
    }

    fun onSaveClicked() {
        if (selectedArticle.value?.saved == false) {
            viewModelScope.launch {
                selectedArticle.value?.id?.let { repository.saveArticle(it) }
                _updateRecyclerOnBack.value = true
            }
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

}