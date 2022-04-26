package com.interview.diagnal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val dataMovies = MutableLiveData<MutableList<MovieModel>>()

    init {
        dataMovies.value = mutableListOf()
    }

    fun loadData(pageNo: Int) {
        val dataOfPage = repository.getMovies(pageNo)
        dataMovies.value?.addAll(dataOfPage)
        dataMovies.postValue(dataMovies.value)
    }

    /* fun loadNetworkData(){
         viewModelScope.launch(Dispatchers.IO) {
             repository.getMoviesNetwork(1)
         }
     }*/
}