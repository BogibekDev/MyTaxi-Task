package dev.bogibek.mytaxitask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

        }

    }



    private fun loadLastLocation() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            getLastLocation().collect { location ->
                _state.value = _state.value.copy(
                    location = location,
                    loading = false
                )
            }
        }
    }
}
