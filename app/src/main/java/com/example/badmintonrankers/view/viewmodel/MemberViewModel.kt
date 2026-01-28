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
import kotlinx.coroutines.launch

class MemberViewModel (
    application: Application
) : AndroidViewModel(application){


    private val repository: BadminRepository

    init {
        val playerDao = PlayerDB.getDatabase(application).playerDao()
        repository = BadminRepository(playerDao)
    }

    private val _addPlayerSuccess = MutableStateFlow<Boolean?>(false)
    val addPlayerSuccess: StateFlow<Boolean?> = _addPlayerSuccess

    fun addPlayer(players: Players) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addPlayer(players)
                _addPlayerSuccess.emit(true)
            } catch (e: Exception) {
                _addPlayerSuccess.emit(false)
            }
        }
    }
}