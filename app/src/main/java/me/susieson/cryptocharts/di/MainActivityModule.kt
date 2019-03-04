package me.susieson.cryptocharts.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.susieson.cryptocharts.ui.main.MainActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
