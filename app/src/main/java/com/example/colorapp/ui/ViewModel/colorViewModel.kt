package com.example.colorapp.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorapp.ui.data.ColorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class ColorViewModel(
    private val repository: ColorRepository
) : ViewModel() {
    val colors = repository.allColors.stateIn(
        viewModelScope,

        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val unsyncedCount = repository.unsyncedCount.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        0
    )

    fun addRandomColor() {
        viewModelScope.launch {
            val color = String.format("#%06X", Random.nextInt(0xFFFFFF + 1))
            repository.addColor(color, System.currentTimeMillis())
        }
    }

    fun syncColors() {
        viewModelScope.launch {
            try {
                repository.syncColors()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}