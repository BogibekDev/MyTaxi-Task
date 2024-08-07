package dev.bogibek.mytaxitask.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bogibek.mytaxitask.data.location.DefaultLocationTracker
import dev.bogibek.mytaxitask.domain.location.LocationTracker
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // TODO: change it
    @Binds
    @Singleton
    abstract fun bindLocationRepository(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}