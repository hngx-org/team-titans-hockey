package com.example.titans_hockey_challenge

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {

    private val _puckColor = MutableLiveData<Int>()
    val puckColor: LiveData<Int> get() = _puckColor

    private val _puckInnerColor = MutableLiveData<Int>()
    val puckInnerColor: LiveData<Int> get() = _puckInnerColor

    private val _paddleColor = MutableLiveData<Int>()
    val paddleColor: LiveData<Int> get() = _paddleColor

    private val _paddleMiddleColor = MutableLiveData<Int>()
    val paddleMiddleColor: LiveData<Int> get() = _paddleMiddleColor

    private val _paddleOuterColor = MutableLiveData<Int>()
    val paddleOuterColor: LiveData<Int> get() = _paddleOuterColor

    fun setPuckColor(color: Int) {
        _puckColor.value = color

        val innerShade = ColorUtils.blendARGB(color, Color.BLACK, 0.2f)
        _puckInnerColor.value = innerShade
    }

    fun setPaddleColor(color: Int) {
        _paddleColor.value = color

        val middleShade = ColorUtils.blendARGB(color, Color.BLACK, 0.4f)
        _paddleMiddleColor.value = middleShade

        val outerShade = ColorUtils.blendARGB(color, Color.BLACK, 0.2f)
        _paddleOuterColor.value = outerShade
    }
}