package com.example.titans_hockey_challenge.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding !!

    var mediaPlayer: MediaPlayer? = null
    var isSoundOn = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
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
                findNavController().navigate(R.id.twoPlayerFragment)
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
        _binding = null
    }
}
