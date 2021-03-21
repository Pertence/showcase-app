package com.example.app.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.app.model.Images

interface ImagesAPI {

    @GET("api/?image_type=photo&category=buildings")
    suspend fun getImages(@Query("per_page")items: Int): Response<Images>

}