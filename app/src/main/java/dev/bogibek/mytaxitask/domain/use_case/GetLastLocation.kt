package dev.bogibek.mytaxitask.domain.use_case

import dev.bogibek.mytaxitask.domain.repository.AppRepository
import javax.inject.Inject

class GetLastLocation @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke() = repository.getLastLocation()
}