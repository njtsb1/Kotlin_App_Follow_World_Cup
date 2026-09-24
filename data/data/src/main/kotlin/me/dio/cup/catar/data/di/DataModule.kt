package me.dio.cup.catar.data.di

import dagger.Binds
import dagger.Module
import me.dio.cup.catar.data.repository.MatchesRepositoryImpl
import me.dio.cup.catar.domain.repositories.MatchesRepository

@Module
interface DataModule {

    @Binds
    fun providesMatchesRepository(impl: MatchesRepositoryImpl): MatchesRepository
}
