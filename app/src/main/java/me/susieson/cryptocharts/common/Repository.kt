package me.susieson.cryptocharts.common

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import me.susieson.cryptocharts.db.CryptoDao
import me.susieson.cryptocharts.model.Cryptocurrency
import me.susieson.cryptocharts.service.ApiService
import javax.inject.Inject

class Repository
@Inject constructor(
    private val apiService: ApiService,
    private val cryptoDao: CryptoDao,
    private val appExecutors: AppExecutors,
    private val application: Application
) {

    private val cad: String = "CAD"
    private val maxLength: Int = 300
    private var coinList: List<Cryptocurrency> = ArrayList()
    private val pageSize = 8
    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(pageSize)
        .build()

    fun getCoinList(): LiveData<List<Cryptocurrency>> {
        val data: MutableLiveData<List<Cryptocurrency>> = MutableLiveData()
        if (NetworkUtils.isConnected(application.applicationContext)) {
            appExecutors.networkIO().execute {
                val response = apiService.getCoinList().execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.response == "Success") {
                        coinList = ArrayList(body.data.values)
                        getPriceList()
                        data.postValue(coinList)
                    }
                }
            }
        } else {
            appExecutors.diskIO().execute {
                data.postValue(cryptoDao.getList())
            }
        }
        return data
    }

    private fun getPriceList() {
        val symbolList = coinList.map { cryptocurrency -> cryptocurrency.symbol }
        val requestBuilder = StringBuilder()
        var lengthCount = 0
        for (symbol in symbolList) {
            if (lengthCount + symbol.length + 1 > maxLength) {
                requestBuilder.setLength(requestBuilder.length - 1)
                requestPrices(requestBuilder.toString())
                requestBuilder.clear()
                lengthCount = 0
            }
            requestBuilder.append(symbol).append(",")
            lengthCount += symbol.length + 1
        }
    }

    private fun requestPrices(request: String) {
        val response = apiService.getPrices(request, cad).execute()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                for ((symbol, value) in body) {
                    val index = coinList.indexOfFirst { c ->
                        c.symbol == symbol
                    }
                    if (index != -1) {
                        coinList[index].price = value[cad] ?: -1.0
                    }
                }
            }
        }
    }

    fun getCompleteList(): LiveData<PagedList<Cryptocurrency>> {
        return cryptoDao.getAll().toLiveData(config)
    }

    fun updateCoinList(list: List<Cryptocurrency>) {
        appExecutors.diskIO().execute {
            cryptoDao.insertAll(list)
        }
    }

    fun updateFavourite(id: Long, favourite: Boolean) {
        appExecutors.diskIO().execute {
            cryptoDao.updateFavourite(id, favourite)
        }
    }
}