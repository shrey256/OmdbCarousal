package com.chalo.assignments.omdbcarousal.home.di

import android.content.Context
import com.chalo.assignments.omdbcarousal.home.home.HomeActivity
import com.chalo.assignments.omdbcarousal.home.di.modules.NetworkModule
import com.chalo.assignments.omdbcarousal.home.di.modules.ViewModelBuilder
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelBuilder::class])
interface AppComponent {

    @Component.Factory
    interface Factory{

        fun create(@BindsInstance context: Context): AppComponent

    }

    fun inject(homeActivity: HomeActivity)

}