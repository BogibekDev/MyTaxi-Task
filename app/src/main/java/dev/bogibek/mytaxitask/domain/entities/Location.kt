package dev.bogibek.mytaxitask.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bogibek.mytaxitask.domain.utils.getTime

@Entity(tableName = "location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val longitude: Double,
    val latitude: Double,
    val time: String = getTime(),
)
