package me.susieson.cryptocharts.ui.splash

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import me.susieson.cryptocharts.R
import me.susieson.cryptocharts.common.Repository
import me.susieson.cryptocharts.model.Cryptocurrency
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SplashViewModel
@Inject constructor(application: Application, private val repository: Repository) : AndroidViewModel(application) {

    private val context: Context = application.applicationContext
    private val dateFormat: DateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.CANADA)

    val title: String = context.resources.getString(R.string.app_name)
    val name: String = context.resources.getString(R.string.app_author)
    val date: String = dateFormat.format(Date())

    fun getCoinList(): LiveData<List<Cryptocurrency>> {
        return repository.getCoinList()
    }

    fun updateCoinList(list: List<Cryptocurrency>) {
        repository.updateCoinList(list)
    }
}