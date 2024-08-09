package dev.bogibek.mytaxitask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bogibek.mytaxitask.data.location.DefaultLocationTracker
import dev.bogibek.mytaxitask.domain.entities.Location
import dev.bogibek.mytaxitask.domain.use_case.AddNewLocation
import dev.bogibek.mytaxitask.domain.use_case.GetLastLocation
import dev.bogibek.mytaxitask.presentation.HomeScreenIntent
import dev.bogibek.mytaxitask.presentation.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationTracker: DefaultLocationTracker,
    private val addNewLocation: AddNewLocation,
    private val getLastLocation: GetLastLocation
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state


    init {
        handleIntent(HomeScreenIntent.LoadLastLocation)
    }

    private fun handleIntent(intent: HomeScreenIntent) {

        when (intent) {
            is HomeScreenIntent.LoadLastLocation -> loadLastLocation()
            else -> Unit
        }

    }

    private fun addMyLocation() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            try {
                val location = locationTracker.getCurrentLocation()
                if (location != null) {
                    addNewLocation(
                        Location(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                    handleIntent(HomeScreenIntent.LoadLastLocation)
                } else {
                    _state.value = _state.value.copy(
                        location = null,
                        loading = false,
                        error = "Something went wrong please try again!"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    location = null,
                    loading = false,
                    error = "Something went wrong please try again!"
                )
            }

        }
    }


    private fun loadLastLocation() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                getLastLocation().collect { location ->
                    _state.value = _state.value.copy(
                        location = location,
                        loading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    location = null,
                    loading = false,
                    error = "Something went wrong please try again!"
                )
            }
        }
    }
}
