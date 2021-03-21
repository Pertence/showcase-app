package com.example.app.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.app.model.DetailLocation
import com.example.app.model.ListLocations

interface LocationAPI {

   @GET("/locations")
   suspend fun getAllLocations(): Response<ListLocations>

   @GET("/locations/{id}")
   suspend fun getDetailsLocation(@Path("id")id: Int): Response<DetailLocation>

}

