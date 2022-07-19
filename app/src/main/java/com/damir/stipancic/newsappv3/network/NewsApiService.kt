package com.damir.stipancic.newsappv3.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/"
private const val SOURCE = "bbc-news"
private const val API_KEY = "390b39b88a9c4ea5a7e410de97866b61"

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(@Query("sources") source: String = SOURCE,
                        @Query("apiKey") api_key: String = API_KEY): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSearchedNews(@Query("query") searchQuery: String,
                                @Query("page") pageNumber: Int = 1,
                                @Query("apiKey") api_key: String = API_KEY): Response<NewsResponse>
}

object NewsApi {
    private val retrofit by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    val retrofitService : NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}