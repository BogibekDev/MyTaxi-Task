package dev.bogibek.mytaxitask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.bogibek.mytaxitask.domain.entities.Location

@Database(entities = [Location::class], version = 1)
abstract class LocationDatabase :RoomDatabase(){

    abstract val dao: LocationDao
}