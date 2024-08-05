package dev.bogibek.mytaxitask.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.bogibek.mytaxitask.data.local.LocationDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocationDatabase(application: Application): LocationDatabase {
        return Room.databaseBuilder(
            application,
            LocationDatabase::class.java,
            "location.db"
        ).build()

    }
}