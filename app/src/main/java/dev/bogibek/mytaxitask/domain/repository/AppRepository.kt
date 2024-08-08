package dev.bogibek.mytaxitask.domain.repository

import dev.bogibek.mytaxitask.domain.entities.Location
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun addNewLocation(location: Location)
    suspend fun getLastLocation(): Flow<Location?>
    suspend fun getAllLocation(): Flow<List<Location>>
}