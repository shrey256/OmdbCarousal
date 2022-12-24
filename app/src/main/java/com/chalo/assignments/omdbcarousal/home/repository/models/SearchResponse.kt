package com.chalo.assignments.omdbcarousal.home.repository.models

data class SearchResponse(
    val Search: List<Media>,
    val totalResults: String,
    val Response: String
)
