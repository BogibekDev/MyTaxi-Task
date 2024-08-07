package dev.bogibek.mytaxitask.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bogibek.mytaxitask.domain.location.LocationTracker
import dev.bogibek.mytaxitask.utils.UIState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = mutableStateOf(UIState())
    val state = _state


}
