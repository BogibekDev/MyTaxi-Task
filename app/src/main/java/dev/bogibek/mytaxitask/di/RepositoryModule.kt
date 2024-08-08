package dev.bogibek.mytaxitask.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bogibek.mytaxitask.data.repository.AppRepositoryImpl
import dev.bogibek.mytaxitask.domain.repository.AppRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository
}