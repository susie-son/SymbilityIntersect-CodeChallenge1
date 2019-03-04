package me.susieson.cryptocharts.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import me.susieson.cryptocharts.common.Repository
import me.susieson.cryptocharts.model.Cryptocurrency
import javax.inject.Inject

class MainFragmentViewModel
@Inject constructor(private val repository: Repository) : ViewModel(),
    CryptoListAdapter.OnItemClickListener {

    fun getCompleteList(): LiveData<PagedList<Cryptocurrency>> {
        return repository.getCompleteList()
    }

    override fun onItemClick(id: Long, favourite: Boolean) {
        repository.updateFavourite(id, favourite)
    }
}
