package com.damir.stipancic.newsappv3.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "articles", indices = [Index(value = [ "url"], unique = true)])
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    var saved: Boolean = false,
    var createdAt: Long = System.currentTimeMillis()
) : Parcelable

