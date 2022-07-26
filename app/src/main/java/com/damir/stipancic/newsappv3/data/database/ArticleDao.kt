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
    suspend fun getSavedArticles(): MutableList<Article>

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<Article>

    @Query("SELECT * FROM articles ORDER BY createdAt DESC LIMIT 10")
    suspend fun getLatestArticles(): List<Article>

    @Query("SELECT COUNT(id) FROM articles WHERE saved = false")
    suspend fun getUnsavedRowCount(): Int

    @Query("DELETE FROM articles WHERE id IN (SELECT id FROM articles WHERE saved = false ORDER BY createdAt ASC LIMIT 10)")
    suspend fun deleteNotNeededArticles()

    @Query("SELECT * FROM articles ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatestEntry(): Article

    @Query("UPDATE articles SET saved = true WHERE id = :id")
    suspend fun saveArticle(id: Long)
}