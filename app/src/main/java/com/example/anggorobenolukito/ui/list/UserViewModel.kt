package com.example.anggorobenolukito.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.anggorobenolukito.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    companion object {
        private const val DEFAULT_QUERY = "\""
    }

    val user = currentQuery.switchMap { currentQuery ->
        useCase.getUserSearchResults(currentQuery).cachedIn(viewModelScope)
    }

    fun searchUsers(query: String) {
        currentQuery.value = query
    }


}