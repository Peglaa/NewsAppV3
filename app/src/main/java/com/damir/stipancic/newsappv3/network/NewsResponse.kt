package com.damir.stipancic.newsappv3.network

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)