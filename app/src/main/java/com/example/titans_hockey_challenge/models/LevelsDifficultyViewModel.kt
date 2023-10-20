package com.example.titans_hockey_challenge.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LevelsDifficultyViewModel : ViewModel() {
    private val _gameDifficulty = MutableLiveData(0f)
    val gameDifficulty: LiveData<Float> get() = _gameDifficulty


    fun setDifficultyLevel(difficulty: Float) {
        _gameDifficulty.value = difficulty
    }
    fun setEasyLevel() {
        _gameDifficulty.value = 5f
    }

    fun setMediumLevel() {
        _gameDifficulty.value = 10f
    }

    fun setHardLevel() {
        _gameDifficulty.value = 15f
    }
}
