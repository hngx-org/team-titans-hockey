package com.example.titans_hockey_challenge.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    var mediaPlayer: MediaPlayer? = null
    var isSoundOn = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.soundtrack)
            mediaPlayer?.isLooping = true

                if (isSoundOn) {
                    mediaPlayer?.start()
                }

            soundbtn.setOnClickListener {
                toggleSound()
            }

            btnSinglePlayer.setOnClickListener {
                findNavController().navigate(R.id.hockeyFragment)
            }

            btnMultiPlayer.setOnClickListener {
                Toast.makeText(context, "Coming Soon!.....", Toast.LENGTH_SHORT).show()
            }

            btnSettings.setOnClickListener {
                findNavController().navigate(R.id.settingsFragment)
            }

        findNavController().popBackStack(R.id.starterFragment,true)

        }

    }


    fun toggleSound() {
        isSoundOn = !isSoundOn

        if (isSoundOn) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(requireContext(), R.raw.soundtrack)
                mediaPlayer?.isLooping = true
                mediaPlayer?.setOnPreparedListener {
                    mediaPlayer?.start()
                }
                mediaPlayer?.prepareAsync()
            } else {
                mediaPlayer?.start()
            }
            binding.soundbtn.setImageResource(R.drawable.volume_max)
        } else {
            mediaPlayer?.pause()
            binding.soundbtn.setImageResource(R.drawable.volume_off_02)
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
    }


}
