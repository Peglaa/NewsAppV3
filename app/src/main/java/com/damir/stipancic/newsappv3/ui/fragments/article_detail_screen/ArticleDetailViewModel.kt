package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import android.util.Log
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

    init {
        _selectedArticle.value = article
    }

    fun onSaveClicked(){
        viewModelScope.launch {
            try {
                selectedArticle.value?.id?.let { repository.saveArticle(it) }
            }
            catch (e: Exception){
                Log.d("articleDetailsViewModel", "onSaveClickedFailed: ${e.message}")
            }
        }

    }

}