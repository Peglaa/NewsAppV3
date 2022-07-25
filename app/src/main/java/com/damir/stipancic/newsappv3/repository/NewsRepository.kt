package com.damir.stipancic.newsappv3.repository

import android.util.Log
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.data.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(private val database: ArticleDatabase) {

    suspend fun refreshNews(){
        withContext(Dispatchers.IO) {
            val response = NewsApi.retrofitService.getNews()
            response.body()?.let { newsResponse ->
                newsResponse.articles.forEach{
                    database.sleepDatabaseDao.insertArticle(it)
                }
            }

            Log.d("newsRepository", "UNSAVED_COUNT: ${database.sleepDatabaseDao.getUnsavedRowCount()}")
            if(database.sleepDatabaseDao.getUnsavedRowCount() >= 20)
                database.sleepDatabaseDao.deleteNotNeededArticles()
            Log.d("newsRepository", "UNSAVED_COUNT_AFTER_DELETE: ${database.sleepDatabaseDao.getUnsavedRowCount()}")
        }
    }

    suspend fun getArticlesFromDB(): List<Article> {
        return database.sleepDatabaseDao.getAllArticles()
    }
}