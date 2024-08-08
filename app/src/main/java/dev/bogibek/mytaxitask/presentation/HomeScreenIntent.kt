package dev.bogibek.mytaxitask.presentation

sealed class HomeScreenIntent {
    data object LoadLastLocation : HomeScreenIntent()
}