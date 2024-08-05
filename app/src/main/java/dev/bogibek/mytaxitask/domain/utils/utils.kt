package dev.bogibek.mytaxitask.domain.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
private fun today():String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return  LocalDateTime.now().format(formatter)
}