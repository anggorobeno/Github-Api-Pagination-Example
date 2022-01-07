package com.example.anggorobenolukito.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.anggorobenolukito.data.remote.network.ApiService
import com.example.anggorobenolukito.data.remote.response.ItemsItem
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    fun getUserSearchResults(query: String) : LiveData<PagingData<ItemsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
            ),pagingSourceFactory = {UserPagingSource(apiService,query)}
        ).liveData

    }
}