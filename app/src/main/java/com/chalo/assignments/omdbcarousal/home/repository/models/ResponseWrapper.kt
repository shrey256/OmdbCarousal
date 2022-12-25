package com.chalo.assignments.omdbcarousal.home.repository.models

data class ResponseWrapper<T>(val response: T?, val error: ResponseError?) {

}

data class ResponseError(val errorMessage: String)