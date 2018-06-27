package jfyg.etherscan.core.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, StorageModule::class])
interface CoreComponent {

    fun context(): Context

    fun sharedPreferences(): SharedPreferences
}