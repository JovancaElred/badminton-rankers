package com.example.badmintonrankers.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _data = MutableStateFlow<List<Players>>(emptyList())
    val data: StateFlow<List<Players>> = _data

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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

    fun getMemberData(){
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)
            try {
                repository.getPlayer().collect{ players ->
                    _data.value = players
                }
            } catch (e: Exception){
                _error.emit(e.message)
            } finally {
                _isLoading.emit(false)
            }
        }


    }
}