package com.chalo.assignments.omdbcarousal.home

import android.app.Application
import com.chalo.assignments.omdbcarousal.home.di.AppComponent
import com.chalo.assignments.omdbcarousal.home.di.DaggerAppComponent

class OmdbCarousalApplication: Application() {

    val appComponent: AppComponent by lazy {
        initializeAppComponent()
    }

    fun initializeAppComponent(): AppComponent{
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
    }

}