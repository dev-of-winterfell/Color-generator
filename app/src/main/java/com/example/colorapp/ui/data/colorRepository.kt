package com.example.colorapp.ui.data

import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class ColorRepository(
    private val colorDao: ColorDao,
    private val firebaseDatabase: DatabaseReference
) {
    val allColors = colorDao.getAllColors()
    val unsyncedCount = colorDao.getUnsyncedCount()

    suspend fun addColor(color: String, timestamp: Long) {
        colorDao.insertColor(ColorEntity(color = color, timestamp = timestamp))
    }

    suspend fun syncColors() {
        allColors.first().filter { !it.isSynced }.forEach { color ->
            firebaseDatabase.child("colors").push().setValue(
                mapOf(
                    "color" to color.color,
                    "timestamp" to color.timestamp
                )
            ).await()
            colorDao.markAsSynced(color.id)
        }
    }
}