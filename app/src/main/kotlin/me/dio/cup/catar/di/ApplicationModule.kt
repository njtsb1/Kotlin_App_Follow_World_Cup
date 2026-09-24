package me.dio.cup.catar.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.dio.cup.catar.data.di.DataModule
import me.dio.cup.catar.local.di.LocalModule
import me.dio.cup.catar.remote.di.NetworkModule
import me.dio.cup.catar.remote.di.RemoteModule
import me.dio.cup.catar.remote.di.ServiceModules

@Module(
    includes = [
        DataModule::class,
        LocalModule::class,
        RemoteModule::class,
        NetworkModule::class,
        ServiceModules::class,
    ]
)
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}
