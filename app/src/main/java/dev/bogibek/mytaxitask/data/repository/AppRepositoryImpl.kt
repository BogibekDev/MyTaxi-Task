package dev.bogibek.mytaxitask.data.repository

import dev.bogibek.mytaxitask.data.local.LocationDao
import dev.bogibek.mytaxitask.domain.entities.Location
import dev.bogibek.mytaxitask.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val dao: LocationDao
) : AppRepository {
    override suspend fun addNewLocation(location: Location) = dao.insertLocation(location)
    override suspend fun getLastLocation(): Flow<Location?> = dao.getLastLocation()
    override suspend fun getAllLocation(): Flow<List<Location>> = dao.getAllLocations()
}