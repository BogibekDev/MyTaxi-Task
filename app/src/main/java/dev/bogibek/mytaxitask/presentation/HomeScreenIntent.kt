package dev.bogibek.mytaxitask.presentation

sealed class HomeScreenIntent {
    data object LoadLastLocation : HomeScreenIntent()
    data object AddNewLocation : HomeScreenIntent()
}