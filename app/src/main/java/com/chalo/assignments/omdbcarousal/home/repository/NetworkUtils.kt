package com.chalo.assignments.omdbcarousal.home.repository

object NetworkUtils {

    const val URL = "http://www.omdbapi.com"
    const val API_KEY = "f5ec82e3"
    const val CACHE_PERIOD = 10 // 10 Minutes
    const val CACHE_DIR = "http-cache"
    const val CACHE_SIZE: Long = 10 * 1024 * 1024; // 10 MiB


}