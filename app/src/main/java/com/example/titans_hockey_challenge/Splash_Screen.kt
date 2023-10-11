package com.example.titans_hockey_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.titans_hockey_challenge.databinding.ActivitySpalshScreenBinding

class Splash_Screen : AppCompatActivity() {
    lateinit var binding:ActivitySpalshScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imgAnim = AnimationUtils.loadAnimation(this,R.anim.slide)
        val btmAnim = AnimationUtils.loadAnimation(this,R.anim.btm)

        binding.apply {
            imageView2.startAnimation(imgAnim)
            appName.startAnimation(btmAnim)
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@Splash_Screen, MainActivity::class.java))
                finish()
            }, 3000)


        }


    }
}
