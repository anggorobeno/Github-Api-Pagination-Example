package com.example.anggorobenolukito.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.anggorobenolukito.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    companion object {
        private const val DEFAULT_QUERY = "\""
    }

    val user = currentQuery.switchMap { currentQuery ->
        repository.getUserSearchResults(currentQuery).cachedIn(viewModelScope)
    }

    fun searchUsers(query : String){
        currentQuery.value = query
    }


}