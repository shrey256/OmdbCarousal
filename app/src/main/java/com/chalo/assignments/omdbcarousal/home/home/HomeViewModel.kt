package com.chalo.assignments.omdbcarousal.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chalo.assignments.omdbcarousal.home.repository.HomeRepository
import com.chalo.assignments.omdbcarousal.home.repository.NetworkUtils
import com.chalo.assignments.omdbcarousal.home.repository.models.Media
import com.chalo.assignments.omdbcarousal.home.repository.models.ResponseError
import com.chalo.assignments.omdbcarousal.home.repository.models.ResponseWrapper
import com.chalo.assignments.omdbcarousal.home.repository.models.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val mediaList: MutableLiveData<ResponseWrapper<List<Media>>> = MutableLiveData()
    private val uiStateFlow = MutableStateFlow(HomeState.STATE_LOADING)

    fun searchMedia(searchString: String): LiveData<ResponseWrapper<List<Media>>>{
        mediaList.value?.let { responseWrapper ->
            responseWrapper.response?.let {
                if(it.isNotEmpty()){
                    return mediaList
                }
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
                mediaList.postValue(ResponseWrapper(null, ResponseError(exception.message ?: NetworkUtils.ERROR_GENERIC)))
            }
            call?.let {
                if(it.isSuccessful){
                    uiStateFlow.emit(HomeState.STATE_SUCCESS)
                    it.body()?.let {  response ->
                        mediaList.postValue(ResponseWrapper(response.Search ?: ArrayList(), null))
                    }
                }
                else{
                    uiStateFlow.emit(HomeState.STATE_ERROR)
                    mediaList.postValue(ResponseWrapper(null, ResponseError(NetworkUtils.ERROR_GENERIC)))
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