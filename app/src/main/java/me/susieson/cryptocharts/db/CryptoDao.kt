package me.susieson.cryptocharts.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.susieson.cryptocharts.model.Cryptocurrency

@Dao
interface CryptoDao {

    @Query("SELECT * FROM cryptocurrency ORDER BY favourite DESC")
    fun getAll(): DataSource.Factory<Int, Cryptocurrency>

    @Query("SELECT * FROM cryptocurrency ORDER BY favourite DESC")
    fun getList(): List<Cryptocurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cryptoList: List<Cryptocurrency>)

    @Query("UPDATE cryptocurrency SET favourite = :favourite WHERE id = :id")
    fun updateFavourite(id: Long, favourite: Boolean)
}
