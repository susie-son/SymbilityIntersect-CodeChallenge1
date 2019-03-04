package me.susieson.cryptocharts.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.susieson.cryptocharts.model.Cryptocurrency

@Database(entities = [Cryptocurrency::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
}