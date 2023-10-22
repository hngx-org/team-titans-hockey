package com.example.titans_hockey_challenge.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentSettingsBinding
import com.example.titans_hockey_challenge.viewmodels.SoundViewModel


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    private val soundViewModel: SoundViewModel by activityViewModels()
    var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            soundViewModel.isSoundOn.observe(viewLifecycleOwner) { isSoundOn ->
                if (isSoundOn) {
                    mediaPlayer?.start()
                    view.findViewById<ImageView>(R.id.soundbtn)
                        ?.setImageResource(R.drawable.volume_max)
                    view.findViewById<TextView>(R.id.switchTv)?.setText(R.string.on)
                } else {
                    mediaPlayer?.pause() // Pause the music if sound is off
                    view.findViewById<ImageView>(R.id.soundbtn)
                        ?.setImageResource(R.drawable.volume_off_02)
                    view.findViewById<TextView>(R.id.switchTv)?.setText(R.string.off)
                }
            }

            switchTv.setOnClickListener {
                toggleSound()
            }

            soundTv.setOnClickListener {
                toggleSound()
            }

            howToPlayTV.setOnClickListener {
                findNavController().navigate(R.id.rulesFragment)
            }

            abtTv.setOnClickListener {
                findNavController().navigate(R.id.aboutFragment)
            }

            bckbtn.setOnClickListener {
                findNavController().navigate(R.id.gameFragment)
            }

        }

    }

    fun toggleSound() {
        val isSoundOn = soundViewModel.isSoundOn.value ?: true
        soundViewModel.setSoundOn(!isSoundOn)

    }

}