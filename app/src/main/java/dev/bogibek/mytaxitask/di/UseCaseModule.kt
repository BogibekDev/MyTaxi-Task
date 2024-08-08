package dev.bogibek.mytaxitask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bogibek.mytaxitask.domain.repository.AppRepository
import dev.bogibek.mytaxitask.domain.use_case.AddNewLocation
import dev.bogibek.mytaxitask.domain.use_case.GetAllLocations
import dev.bogibek.mytaxitask.domain.use_case.GetLastLocation

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideAddNewLocation(repository: AppRepository) = AddNewLocation(repository)

    @Provides
    fun provideGetLastLocation(repository: AppRepository) = GetLastLocation(repository)

    @Provides
    fun provideGetAllLocation(repository: AppRepository) = GetAllLocations(repository)

}