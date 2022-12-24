package com.chalo.assignments.omdbcarousal.home.di.modules

import android.content.Context
import com.chalo.assignments.omdbcarousal.home.repository.ApiService
import com.chalo.assignments.omdbcarousal.home.repository.NetworkUtils
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory): Retrofit{
            return Retrofit
                .Builder()
                .baseUrl(NetworkUtils.URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor,
                            cache: Cache,
                            cacheInterceptor: CacheInterceptor): OkHttpClient{
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideCacheDirectory(context: Context): File {
        return File(context.cacheDir, NetworkUtils.CACHE_DIR)
    }

    @Provides
    fun provideCache(cacheDir: File): Cache{
        return Cache(cacheDir, NetworkUtils.CACHE_SIZE)
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

}