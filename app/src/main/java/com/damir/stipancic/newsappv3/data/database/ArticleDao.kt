package com.damir.stipancic.newsappv3.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damir.stipancic.newsappv3.data.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(article: Article) : Long

    @Query("SELECT * FROM articles WHERE saved = true")
    suspend fun getSavedArticles(): List<Article>

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<Article>

    @Query("SELECT COUNT(id) FROM articles")
    suspend fun getRowCount(): Int
}