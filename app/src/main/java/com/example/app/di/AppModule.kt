package com.example.app.di

import com.example.app.network.ImagesAPI
import com.example.app.network.LocationAPI
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val BASE_URL_LOCATION_API = "https://hotmart-mobile-app.herokuapp.com"

    private const val BASE_URL_IMAGES_API = "https://pixabay.com/"

    private const val BASE_URL_IMAGES_KEY = ""

    private val interceptor = Interceptor { chain ->
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("key", BASE_URL_IMAGES_KEY)
            .build()
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun provideLocationsAPI(): LocationAPI {
        return  Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL_LOCATION_API)
            .build()
            .create(LocationAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideImagesAPI(): ImagesAPI {
        return  Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL_IMAGES_API)
            .client(okHttpClient)
            .build()
            .create(ImagesAPI::class.java)
    }

}