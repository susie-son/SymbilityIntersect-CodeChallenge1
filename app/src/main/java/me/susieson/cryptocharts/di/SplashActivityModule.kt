package me.susieson.cryptocharts.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.susieson.cryptocharts.ui.splash.SplashActivity

@Module
abstract class SplashActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity
}
