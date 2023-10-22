package com.example.titans_hockey_challenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LevelsDifficultySharedViewModel : ViewModel() {
    private val _gameDifficulty = MutableLiveData(0f)
    val gameDifficulty: LiveData<Float> get() = _gameDifficulty

    fun setEasyLevel() {
        _gameDifficulty.value = 8f
    }
    fun setMediumLevel() {
        _gameDifficulty.value = 15f
    }
    fun setHardLevel() {
        _gameDifficulty.value = 25f
    }


}
