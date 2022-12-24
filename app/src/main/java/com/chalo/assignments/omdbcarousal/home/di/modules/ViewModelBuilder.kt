package com.chalo.assignments.omdbcarousal.home.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chalo.assignments.omdbcarousal.home.home.HomeViewModel
import com.chalo.assignments.omdbcarousal.home.di.ViewModelFactory
import com.chalo.assignments.omdbcarousal.home.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilder {

    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel

}
