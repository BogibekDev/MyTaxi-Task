package dev.bogibek.mytaxitask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.bogibek.mytaxitask.domain.entities.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Query("SELECT * FROM location ORDER BY time DESC LIMIT 1")
    fun getLastLocation(): Flow<Location?>

    @Query("SELECT * FROM location")
    fun getAllLocations(): Flow<List<Location>>

}