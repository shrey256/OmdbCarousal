package com.chalo.assignments.omdbcarousal.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.chalo.assignments.omdbcarousal.R
import com.chalo.assignments.omdbcarousal.databinding.ActivityHomeBinding
import com.chalo.assignments.omdbcarousal.home.OmdbCarousalApplication
import com.chalo.assignments.omdbcarousal.home.repository.NetworkUtils.MAX_LIST_SIZE
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: MediaViewPagerAdapter

    private lateinit var viewBinding: ActivityHomeBinding

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    private var job: Job? = null

    private val slideInterval = 5.seconds

    private val pageTransformer = ViewPager2.PageTransformer { page, position ->
        page.apply {
            val r = 1 - abs(position)
            page.scaleY = 0.75f + r * 0.25f
            page.scaleX = 0.75f + r * 0.25f
        }
    }

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
        viewBinding.vpCarousal.setPageTransformer(pageTransformer)
        
        viewBinding.ivError.setOnClickListener {
            fetchMedia()
        }
    }

    private fun initViewpagerTouchListeners(){
        viewBinding.vpCarousal.post {
            if(adapter.itemCount > 0){
                var index = 0
                while(index < adapter.itemCount){
                    viewBinding.vpCarousal.getChildAt(index)?.setOnTouchListener { _, motionEvent -> when (motionEvent?.action) {
                        MotionEvent.ACTION_UP -> {
                            launchSlideShow()
                        }
                        MotionEvent.ACTION_DOWN -> {
                            job?.cancel()
                        }
                    }
                    return@setOnTouchListener false
                    }
                    index++
                }
            }
        }
    }


    private fun fetchMedia(){
        viewModel.searchMedia("christmas").observe(this
        ) {
            it.response?.let { list ->
                if(list.size > MAX_LIST_SIZE) {
                    adapter.addAll(list.subList(0, MAX_LIST_SIZE))
                }
                else{
                    adapter.addAll(list)
                }
                initViewpagerTouchListeners()
                launchSlideShow()
            }
            it.error?.let { error ->
                viewBinding.tvError.text = "$error.errorMessage\n(Tap icon to retry)"
            }
        }

        lifecycleScope.launch {
            viewModel.getUiState().collect(){
                when(it){
                    HomeViewModel.HomeState.STATE_LOADING -> {
                        viewBinding.pbLoader.visibility = View.VISIBLE
                        viewBinding.ivError.visibility = View.GONE
                        viewBinding.tvError.visibility = View.GONE
                    }
                    HomeViewModel.HomeState.STATE_ERROR -> {
                        viewBinding.pbLoader.visibility = View.GONE
                        viewBinding.ivError.visibility = View.VISIBLE
                        viewBinding.tvError.visibility = View.VISIBLE
                    }
                    HomeViewModel.HomeState.STATE_SUCCESS  -> {
                        viewBinding.pbLoader.visibility = View.GONE
                        viewBinding.ivError.visibility = View.GONE
                        viewBinding.tvError.visibility = View.GONE
                    }
                    HomeViewModel.HomeState.STATE_EMPTY -> {
                        viewBinding.pbLoader.visibility = View.GONE
                        viewBinding.tvError.text = getString(R.string.empty_list)
                        viewBinding.tvError.visibility = View.VISIBLE
                        viewBinding.ivError.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        job?.cancel()
    }

    override fun onResume() {
        super.onResume()
        launchSlideShow()
    }

    private fun launchSlideShow(){
        if(adapter.itemCount == 0){
            return
        }
        job = tickerFlow(slideInterval, slideInterval).onEach {
            viewBinding.vpCarousal.setCurrentItem(((viewBinding.vpCarousal.currentItem + 1) % adapter.itemCount), true)
        }.launchIn(lifecycleScope)
    }


    @OptIn(ExperimentalTime::class)
    private fun tickerFlow(period: Duration, initialDelay: Duration) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
    }

}