package com.chalo.assignments.omdbcarousal.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalo.assignments.omdbcarousal.home.repository.HomeRepository
import com.chalo.assignments.omdbcarousal.home.repository.models.Media
import com.chalo.assignments.omdbcarousal.home.repository.models.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val mediaList: MutableLiveData<List<Media>> = MutableLiveData()
    private val uiStateFlow = MutableStateFlow(HomeState.STATE_LOADING)

    fun searchMedia(searchString: String): LiveData<List<Media>>{
        mediaList.value?.let {
            if(it.isNotEmpty()){
                return mediaList
            }
        }
        viewModelScope.launch (Dispatchers.IO){
            uiStateFlow.emit(HomeState.STATE_LOADING)
            var call : Response<SearchResponse>? = null
            try {
                call = repository.searchMedia(searchString).execute()
            }
            catch (exception: Exception){
                uiStateFlow.value = HomeState.STATE_ERROR
            }
            call?.let {
                if(it.isSuccessful){
                    uiStateFlow.emit(HomeState.STATE_SUCCESS)
                    it.body()?.let {  response ->
                        mediaList.postValue(response.Search)
                    }
                }
                else{
                    uiStateFlow.emit(HomeState.STATE_ERROR)
                }
            }
        }
        return mediaList;
    }

    fun getUiState(): StateFlow<HomeState>{
        return uiStateFlow
    }

    enum class HomeState{
        STATE_LOADING,
        STATE_ERROR,
        STATE_SUCCESS
    }

}