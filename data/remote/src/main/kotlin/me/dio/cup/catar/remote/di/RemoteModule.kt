package me.dio.cup.catar.remote.di

import dagger.Binds
import dagger.Module
import me.dio.cup.catar.data.source.MatchesDataSource
import me.dio.cup.catar.remote.source.MatchDataSourceRemote

@Module
interface RemoteModule {

    @Binds
    fun providesMatchDataSourceRemote(impl: MatchDataSourceRemote): MatchesDataSource.Remote
}
