package com.jobisity.myserieslist.di

import com.jobisity.myserieslist.data.repository.ShowRepository
import com.jobisity.myserieslist.domain.repository.IShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMyRepository(
        ShowRepository: ShowRepository
    ): IShowRepository
}