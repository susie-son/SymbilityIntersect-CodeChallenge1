package me.susieson.cryptocharts.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.susieson.cryptocharts.ui.main.MainFragment

@Module
abstract class MainFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}
