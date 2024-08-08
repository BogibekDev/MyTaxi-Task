package dev.bogibek.mytaxitask.presentation

import dev.bogibek.mytaxitask.domain.entities.Location

data class HomeScreenState(
    val loading: Boolean = false,
    val location: Location? = null,
    val error: String? = null
)