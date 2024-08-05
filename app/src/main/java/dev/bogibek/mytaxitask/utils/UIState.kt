package dev.bogibek.mytaxitask.utils

sealed class UIState<out T : Any> {
    data object EMPTY : UIState<Nothing>()
    data object LOADING : UIState<Nothing>()
    data class SUCCESS<out T : Any>(val data: T) : UIState<T>()
    data class ERROR(val error: String) : UIState<Nothing>()

}