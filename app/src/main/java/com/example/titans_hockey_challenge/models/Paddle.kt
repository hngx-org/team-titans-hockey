package com.example.titans_hockey_challenge.models

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

data class Paddle(
    var requestWidth: Int,
    val requestHeight: Int,
    var score: Int = 0,
    val paint: Paint,
    val bounds: RectF = RectF(0f, 0f, requestWidth.toFloat(), requestHeight.toFloat())
) {

    fun draw(canvas: Canvas) {
        canvas.drawRoundRect(bounds, 5f, 5f, paint)

    }
}
