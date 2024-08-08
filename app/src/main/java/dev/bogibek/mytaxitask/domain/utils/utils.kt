package dev.bogibek.mytaxitask.domain.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getTime(): String {
    val formatter = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
    return formatter.format(Date())
}