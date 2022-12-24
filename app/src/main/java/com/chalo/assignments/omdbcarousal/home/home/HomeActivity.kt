package com.chalo.assignments.omdbcarousal.home.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chalo.assignments.omdbcarousal.databinding.ActivityHomeBinding
import com.chalo.assignments.omdbcarousal.home.OmdbCarousalApplication
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: MediaViewPagerAdapter

    private lateinit var viewBinding: ActivityHomeBinding

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(viewBinding.root)

        (application as OmdbCarousalApplication).appComponent.inject(this)
        initViews()

        fetchMedia()

    }

    private fun initViews(){
        viewBinding.vpCarousal.adapter = adapter
    }

    private fun fetchMedia(){
        viewModel.searchMedia("new").observe(this
        ) {
            Log.d("HomeActivity", "Response received")
            adapter.addAll(it)
        }

        lifecycleScope.launch {
            viewModel.getUiState().collect(){
                when(it){
                    HomeViewModel.HomeState.STATE_LOADING -> {
                        viewBinding.pbLoader.visibility = View.VISIBLE
                        viewBinding.ivError.visibility = View.GONE
                    }
                    HomeViewModel.HomeState.STATE_ERROR -> {
                        viewBinding.pbLoader.visibility = View.GONE
                        viewBinding.ivError.visibility = View.VISIBLE
                    }
                    HomeViewModel.HomeState.STATE_SUCCESS  -> {
                        viewBinding.pbLoader.visibility = View.GONE
                        viewBinding.ivError.visibility = View.GONE
                    }
                }
            }
        }
    }



}