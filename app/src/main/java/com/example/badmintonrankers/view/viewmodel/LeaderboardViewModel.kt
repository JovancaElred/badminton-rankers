package com.example.badmintonrankers.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.badmintonrankers.data.model.Players
import com.example.badmintonrankers.data.providers.PlayerDB
import com.example.badmintonrankers.data.repository.BadminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    application: Application
) : AndroidViewModel(application){
    private val repository : BadminRepository

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _data = MutableStateFlow<List<Players>>(emptyList())
    val data: StateFlow<List<Players>> = _data

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        val playerDao = PlayerDB.getDatabase(application).playerDao()
        repository = BadminRepository(playerDao)

        getMemberData()
    }

    private fun getMemberData(){
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)
            repository.getPlayer()
                .onStart { _isLoading.value = true }
                .catch { e -> _error.value = e.message }
                .collect { players ->
                    _data.value = players
                    _isLoading.value = false
                }
        }
    }
}