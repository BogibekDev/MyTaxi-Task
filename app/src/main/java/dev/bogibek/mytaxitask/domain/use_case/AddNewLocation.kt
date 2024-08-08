package dev.bogibek.mytaxitask.domain.use_case

import dev.bogibek.mytaxitask.domain.entities.Location
import dev.bogibek.mytaxitask.domain.repository.AppRepository
import javax.inject.Inject

class AddNewLocation @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(location: Location) = repository.addNewLocation(location)
}