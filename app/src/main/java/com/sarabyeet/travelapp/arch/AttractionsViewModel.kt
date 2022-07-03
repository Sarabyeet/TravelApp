package com.sarabyeet.travelapp.arch

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarabyeet.travelapp.data.Attraction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AttractionsViewModel : ViewModel() {
    private val repository = AttractionsRepository()

    // Home fragment list live data
    val attractionsListLiveData = MutableLiveData<List<Attraction>>()

    // Detail fragment live data
    val selectedAttraction = MutableLiveData<Attraction>()

    val factsDialogLiveData = MutableLiveData<Attraction>()

     fun init(context: Context) {
         viewModelScope.launch {
             delay(2000L)
             val attractions = repository.parseAttractions(context)
             attractionsListLiveData.postValue(attractions)
         }

    }

    fun getAttraction(attractionId: String) {
        val attraction = attractionsListLiveData.value?.find {
            it.id == attractionId
        } ?: return
        selectedAttraction.postValue(attraction)
    }
}