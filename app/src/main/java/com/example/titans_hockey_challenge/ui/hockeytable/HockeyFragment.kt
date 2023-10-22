package com.example.titans_hockey_challenge.ui.hockeytable

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentHockeyBinding
import com.example.titans_hockey_challenge.viewmodels.LevelsDifficultySharedViewModel
import com.example.titans_hockey_challenge.viewmodels.SoundViewModel
import com.example.titans_hockey_challenge.utils.GameThread

class HockeyFragment : Fragment() {
    private var mGameThread: GameThread? = null
    private lateinit var hockeyTable: HockeyTable

    private var _binding: FragmentHockeyBinding? = null
    private val binding get() = _binding !!
    private val soundViewModel: SoundViewModel by activityViewModels()
    private val levelsViewModel: LevelsDifficultySharedViewModel by activityViewModels()

    private var pauseBtnClicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentHockeyBinding.inflate(inflater, container, false)
        soundViewModel.setSoundOn(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hockeyTable = binding.hockeyTable

        val initialDrawableResId = R.drawable.volume_max
        val newDrawableResId = R.drawable.volume_off_02
        val initialDrawable: Drawable? = ContextCompat.getDrawable(requireContext(), initialDrawableResId)
        val newDrawable: Drawable? = ContextCompat.getDrawable(requireContext(), newDrawableResId)

        hockeyTable.setScoreOpponent(binding.tvScoreOpponent)
        hockeyTable.setScorePlayer(binding.tvScorePlayer)
        hockeyTable.setStatus(binding.tvStatus)
        mGameThread = hockeyTable.game

        binding.imgPauseButton.setOnClickListener {
            mGameThread?._pause()
            hockeyTable.pauseBackgroundSound()
            binding.pauseOverlay.visibility = View.VISIBLE
        }

        binding.resumeGame.setOnClickListener {
            mGameThread?._resume()
            hockeyTable.resumeGame()
            if (!pauseBtnClicked) {
                hockeyTable.playStartGameSound()
            }
            binding.pauseOverlay.visibility = View.INVISIBLE
        }

        binding.pauseMusic.setOnClickListener {
            pauseBtnClicked = !pauseBtnClicked
            if (pauseBtnClicked) {
                hockeyTable.stopBackgroundSound()
                binding.pauseMusic.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
            } else {
                hockeyTable.playStartGameSound()
                binding.pauseMusic.setCompoundDrawablesWithIntrinsicBounds(null, null, initialDrawable, null)
            }
        }

        binding.exitGame.setOnClickListener {
            findNavController().popBackStack(R.id.gameFragment, false)
        }


        levelsViewModel.gameDifficulty.observe(viewLifecycleOwner) {
            hockeyTable.aiSpeed = it
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        soundViewModel.setSoundOn(true)
    }
}