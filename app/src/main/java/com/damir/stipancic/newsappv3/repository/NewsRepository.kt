package com.damir.stipancic.newsappv3.repository

import android.util.Log
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.data.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val ONE_HOUR_IN_MILLIS = 3_600_000

class NewsRepository(private val database: ArticleDatabase) {

    suspend fun refreshNews(){
        withContext(Dispatchers.IO) {
            //check the age of the data in the DB and update if it's older than 1 hour
            val latestEntry = database.sleepDatabaseDao.getLatestEntry()
            if((System.currentTimeMillis() - latestEntry.createdAt) > ONE_HOUR_IN_MILLIS) {
                val response = NewsApi.retrofitService.getNews()
                response.body()?.let { newsResponse ->
                    newsResponse.articles.forEach {
                        database.sleepDatabaseDao.insertArticle(it)
                    }
                }

                Log.d(
                    "newsRepository",
                    "UNSAVED_COUNT: ${database.sleepDatabaseDao.getUnsavedRowCount()}"
                )
                //Limit database to max 10 entries, to coincide with the amount of news pulled from API
                if (database.sleepDatabaseDao.getUnsavedRowCount() >= 20)
                    database.sleepDatabaseDao.deleteNotNeededArticles()
                Log.d(
                    "newsRepository",
                    "UNSAVED_COUNT_AFTER_DELETE: ${database.sleepDatabaseDao.getUnsavedRowCount()}"
                )
            }
        }
    }

    suspend fun getArticlesFromDB(): List<Article> {
        return database.sleepDatabaseDao.getLatestArticles()
    }

    suspend fun saveArticle(id: Long){
        database.sleepDatabaseDao.saveArticle(id)
    }

    suspend fun getSavedArticles(): List<Article>{
        return database.sleepDatabaseDao.getSavedArticles()
    }
}