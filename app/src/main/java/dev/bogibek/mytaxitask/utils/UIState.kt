package dev.bogibek.mytaxitask.utils

import dev.bogibek.mytaxitask.domain.entities.Location

data class UIState(
    val loading: Boolean = false,
    val location: Location? = null,
    val error: String? = null
)