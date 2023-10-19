package com.example.titans_hockey_challenge.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentGameBinding
import com.example.titans_hockey_challenge.models.SoundViewModel


class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    var mediaPlayer: MediaPlayer? = null
    private val soundViewModel: SoundViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundViewModel.setSoundOn(true)



        binding.apply {
            soundViewModel.isSoundOn.observe(viewLifecycleOwner) { isSoundOn ->
                if (isSoundOn) {
                    mediaPlayer?.start()
                    view.findViewById<ImageView>(R.id.soundbtn)
                        ?.setImageResource(R.drawable.volume_max)
                    view.findViewById<TextView>(R.id.switchTv)?.setText(R.string.on)
                } else {
                    mediaPlayer?.pause()
                    view.findViewById<ImageView>(R.id.soundbtn)
                        ?.setImageResource(R.drawable.volume_off_02)
                    view.findViewById<TextView>(R.id.switchTv)?.setText(R.string.off)
                }
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
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finish()
        }


    }


    fun toggleSound() {
        val isSoundOn = soundViewModel.isSoundOn.value ?: true
        soundViewModel.setSoundOn(!isSoundOn)

    }

}


