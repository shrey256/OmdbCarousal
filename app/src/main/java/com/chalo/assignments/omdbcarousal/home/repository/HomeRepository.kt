package com.chalo.assignments.omdbcarousal.home.repository

import com.chalo.assignments.omdbcarousal.home.repository.models.SearchResponse
import retrofit2.Call
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun searchMedia(searchString: String): Call<SearchResponse>{
        return apiService.searchMedia(searchString, 1)
    }


}