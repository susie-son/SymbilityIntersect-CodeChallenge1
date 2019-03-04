package me.susieson.cryptocharts.model

import com.google.gson.annotations.SerializedName

class CoinListResponse {

    @SerializedName("Response")
    var response: String = ""
    @SerializedName("Data")
    var data: Map<String, Cryptocurrency> = emptyMap()
}