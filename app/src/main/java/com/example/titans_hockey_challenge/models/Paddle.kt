package com.example.titans_hockey_challenge.models

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

data class Paddle(
    var requestWidth: Int,
    val requestHeight: Int,
    var score: Int = 0,
    val paint: Paint,
    val middlePaint : Paint,
    val outerPaint : Paint,
    val bounds: RectF = RectF(0f, 0f, requestWidth.toFloat(), requestHeight.toFloat())
) {
    fun drawCircle(canvas: Canvas) {
        val centerX = bounds.centerX()
        val centerY = bounds.centerY()
        val radius = bounds.width() / 2

        // outermost circle
        canvas.drawCircle(centerX, centerY, radius, outerPaint)

        // middle circle
        val middleRadius = radius * 0.7f
        canvas.drawCircle(centerX, centerY, middleRadius, middlePaint)

        // inner circle
        val innerRadius = radius * 0.4f
        canvas.drawCircle(centerX, centerY, innerRadius, paint)
    }
}