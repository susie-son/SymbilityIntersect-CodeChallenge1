package me.susieson.cryptocharts.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import me.susieson.cryptocharts.R
import me.susieson.cryptocharts.databinding.ActivitySplashBinding
import me.susieson.cryptocharts.model.Cryptocurrency
import me.susieson.cryptocharts.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    private val coinListObserver = Observer<List<Cryptocurrency>> { list ->
        if (list.isNotEmpty()) {
            viewModel.updateCoinList(list)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.model = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        viewModel = binding.model!!
        viewModel.getCoinList().observe(this, coinListObserver)
    }

    override fun onStop() {
        super.onStop()
        viewModel.getCoinList().removeObservers(this)
    }
}
