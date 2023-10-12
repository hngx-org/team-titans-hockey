package com.example.titans_hockey_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.titans_hockey_challenge.databinding.Splash2Binding

class Splash2 : AppCompatActivity() {
    lateinit var binding: Splash2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Splash2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val top = AnimationUtils.loadAnimation(this,R.anim.left_right)
        val btm = AnimationUtils.loadAnimation(this,R.anim.right_left)

        binding.apply {
            hockeyTxt.startAnimation(top)
            challngeTV.startAnimation(btm)

            Handler(Looper.getMainLooper()).postDelayed(
                { startActivity(Intent(this@Splash2, MainActivity::class.java))
                    finish()
                }, 6000
            )

        }

    }

}
