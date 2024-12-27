package com.example.colorapp.ui.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Query("SELECT * FROM colors ORDER BY timestamp DESC")
    fun getAllColors(): Flow<List<ColorEntity>>

    @Query("SELECT COUNT(*) FROM colors WHERE isSynced = 0")
    fun getUnsyncedCount(): Flow<Int>

    @Insert
    suspend fun insertColor(color: ColorEntity)

    @Query("UPDATE colors SET isSynced = 1 WHERE id = :colorId")
    suspend fun markAsSynced(colorId: Int)
}