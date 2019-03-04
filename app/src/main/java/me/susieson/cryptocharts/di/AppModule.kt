package me.susieson.cryptocharts.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.susieson.cryptocharts.common.AppExecutors
import me.susieson.cryptocharts.db.AppDatabase
import me.susieson.cryptocharts.db.CryptoDao
import me.susieson.cryptocharts.service.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val timeout = 60L
        val client = OkHttpClient.Builder()
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://min-api.cryptocompare.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return app
    }

    @Provides
    @Singleton
    fun provideCryptoDao(): CryptoDao {
        return Room.databaseBuilder(app.applicationContext, AppDatabase::class.java, "cryptocharts-database")
            .build().cryptoDao()
    }

    @Provides
    @Singleton
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }
}
