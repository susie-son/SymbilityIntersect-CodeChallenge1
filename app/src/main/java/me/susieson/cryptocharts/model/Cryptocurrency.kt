package me.susieson.cryptocharts.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Cryptocurrency {

    @PrimaryKey
    @SerializedName("Id")
    var id: Long = -1
    @SerializedName("Symbol")
    var symbol: String = ""
    @SerializedName("CoinName")
    var coinName: String = ""
    var price: Double = -1.0
    var favourite: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other is Cryptocurrency) {
            return id == other.id &&
                    symbol == other.symbol &&
                    coinName == other.coinName &&
                    price == other.price &&
                    favourite == other.favourite
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + symbol.hashCode()
        result = 31 * result + coinName.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + favourite.hashCode()
        return result
    }
}