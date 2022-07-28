package com.damir.stipancic.newsappv3.data.network

import com.damir.stipancic.newsappv3.BuildConfig
import com.damir.stipancic.newsappv3.data.models.NewsResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
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
private const val API_KEY = BuildConfig.API_KEY

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getNews(@Query("sources") source: String = SOURCE,
                        @Query("apiKey") api_key: String = API_KEY): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSearchedNews(@Query("q") searchQuery: String,
                                @Query("page") pageNumber: Int = 1,
                                @Query("pageSize") pageSize: Int = 10,
                                @Query("apiKey") api_key: String = API_KEY): Response<NewsResponse>
}

//NEED THIS TO AVOID NULL EXCEPTION FOR SOURCE ID
object NULL_TO_EMPTY_STRING_ADAPTER {
    @FromJson fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}

object NewsApi {
    private val retrofit by lazy {
        val moshi = Moshi.Builder()
            .add(NULL_TO_EMPTY_STRING_ADAPTER)
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