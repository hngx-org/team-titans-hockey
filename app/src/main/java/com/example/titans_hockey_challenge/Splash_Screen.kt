package com.example.titans_hockey_challenge

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.titans_hockey_challenge.databinding.ActivitySpalshScreenBinding

class Splash_Screen : AppCompatActivity() {
    lateinit var binding:ActivitySpalshScreenBinding

    var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.air_hockey_4)

        binding.apply {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@Splash_Screen, MainActivity::class.java))
                finish()
            }, 6000)

            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer?.start()
            }, 1500)

        }
    }
}
