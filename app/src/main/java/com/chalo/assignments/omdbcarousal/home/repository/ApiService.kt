package com.chalo.assignments.omdbcarousal.home.repository

import com.chalo.assignments.omdbcarousal.home.repository.models.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    fun searchMedia(
        @Query("s") searchString: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = NetworkUtils.API_KEY): Call<SearchResponse>

}