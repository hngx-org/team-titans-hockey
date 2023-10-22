package com.example.titans_hockey_challenge.viewmodels

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.titans_hockey_challenge.R

class SoundViewModel(application: Application) : AndroidViewModel(application) {
    private val _isSoundOn = MutableLiveData(true)
    val isSoundOn: LiveData<Boolean> get() = _isSoundOn
    private var mediaPlayer: MediaPlayer? = null

    init {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplication(), R.raw.soundtrack)
            mediaPlayer?.isLooping = true
            if (isSoundOn.value == true) {
                mediaPlayer?.start()
            } else {
                mediaPlayer?.pause()
            }
        }
    }

    fun setSoundOn(isOn: Boolean) {
        _isSoundOn.value = isOn
        if (isOn) {
            if (mediaPlayer == null) {
                mediaPlayer?.isLooping = true
                mediaPlayer?.setOnPreparedListener {
                    mediaPlayer?.start()
                }
                mediaPlayer?.prepareAsync()
            } else {
                mediaPlayer?.start()
            }
        } else {
            mediaPlayer?.pause()
        }
    }


}
