package me.susieson.cryptocharts.service

import me.susieson.cryptocharts.model.CoinListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/all/coinlist")
    fun getCoinList(): Call<CoinListResponse>

    @GET("data/pricemulti")
    fun getPrices(
        @Query("fsyms") fsym: String,
        @Query("tsyms") tsyms: String
    ): Call<HashMap<String, HashMap<String, Double>>>
}