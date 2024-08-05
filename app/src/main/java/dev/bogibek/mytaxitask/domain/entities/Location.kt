package dev.bogibek.mytaxitask.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    val longitude: Double,
    val latitude: Double,
    val time: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)
