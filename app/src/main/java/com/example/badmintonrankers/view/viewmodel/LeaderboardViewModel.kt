package com.example.badmintonrankers.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.badmintonrankers.data.model.Players
import com.example.badmintonrankers.data.providers.PlayerDB
import com.example.badmintonrankers.data.repository.BadminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class LeaderboardViewModel(
    application: Application
) : AndroidViewModel(application){
    private val repository : BadminRepository

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val data: StateFlow<List<Players>>

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        val playerDao = PlayerDB.getDatabase(application).playerDao()
        repository = BadminRepository(playerDao)

        data = repository.getPlayer()
            .map { list -> list.sortedByDescending { it.mmr ?: 0 } }
            .onStart { _isLoading.value = true }
            .catch { e -> _error.value = e.message }
            .onEach { _isLoading.value = false }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    }
}