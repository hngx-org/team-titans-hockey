package com.example.titans_hockey_challenge.models

import android.graphics.Canvas
import android.graphics.Paint
import com.example.titans_hockey_challenge.utils.PUCK_SPEED

class Puck(
    val radius: Float,
    private val paint: Paint
) {
    var centerX = 0f
    var centerY = 0f
    var velocityX: Float = PUCK_SPEED
    var velocityY: Float = PUCK_SPEED
    fun draw(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    fun movePuck(canvas: Canvas) {
        centerX += velocityX
        centerY += velocityY

        // Check Collision
        if (centerY < radius) {
            centerY = radius
        } else if (centerY + radius >= canvas.height) {
            centerY = (canvas.height - radius - 1)
        }
    }

    override fun toString(): String {
        return "CenterX = " + centerX + "CenterY" + centerY + "velX= " + velocityX + " velY= " + velocityY
    }
}