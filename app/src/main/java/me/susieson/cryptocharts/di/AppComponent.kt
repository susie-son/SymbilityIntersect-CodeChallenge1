package me.susieson.cryptocharts.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        SplashActivityModule::class,
        MainActivityModule::class]
)
interface AppComponent {

    fun inject(app: App)
}