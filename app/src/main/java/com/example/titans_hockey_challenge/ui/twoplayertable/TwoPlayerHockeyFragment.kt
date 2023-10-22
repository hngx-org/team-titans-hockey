package com.example.titans_hockey_challenge.ui.twoplayertable

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentTwoPlayerBinding
import com.example.titans_hockey_challenge.viewmodels.SoundViewModel
import com.example.titans_hockey_challenge.utils.TwoPlayerGameThread

class TwoPlayerHockeyFragment : Fragment() {
    private var _binding : FragmentTwoPlayerBinding? = null
    private val binding get() = _binding !!

    private var mTwoPlayerGameThread: TwoPlayerGameThread? = null
    private lateinit var twoPlayerHockeyTable: TwoPlayerHockeyTable

    private val soundViewModel: SoundViewModel by activityViewModels()
    private var pauseBtnClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTwoPlayerBinding.inflate(layoutInflater, container, false)
        soundViewModel.setSoundOn(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val initialDrawableResId = R.drawable.volume_max
        val newDrawableResId = R.drawable.volume_off_02
        val initialDrawable: Drawable? = ContextCompat.getDrawable(requireContext(), initialDrawableResId)
        val newDrawable: Drawable? = ContextCompat.getDrawable(requireContext(), newDrawableResId)

        twoPlayerHockeyTable = binding.twoPlayerHockeyTable
        twoPlayerHockeyTable.setScorePlayer1(binding.tvScorePlayer1)
        twoPlayerHockeyTable.setScorePlayer2(binding.tvScorePlayer2)
        twoPlayerHockeyTable.setStatus(binding.tvStatus2)
        mTwoPlayerGameThread = twoPlayerHockeyTable.twoPlayerGame

        binding.imgPauseButton2.setOnClickListener {
            mTwoPlayerGameThread?._pause()
            twoPlayerHockeyTable.pauseBackgroundSound()
            binding.pauseOverlay2.visibility = View.VISIBLE
        }

        binding.resumeGame2.setOnClickListener {
            mTwoPlayerGameThread?._resume()
            twoPlayerHockeyTable.resumeGame()
            if (!pauseBtnClicked) {
                twoPlayerHockeyTable.playStartGameSound()
            }
            binding.pauseOverlay2.visibility = View.GONE
        }

        binding.pauseMusic2.setCompoundDrawablesWithIntrinsicBounds(null, null, initialDrawable, null)
        binding.pauseMusic2.setOnClickListener {

            pauseBtnClicked = !pauseBtnClicked
            if (pauseBtnClicked) {
                twoPlayerHockeyTable.stopBackgroundSound()
                binding.pauseMusic2.setCompoundDrawablesWithIntrinsicBounds(null, null, newDrawable, null)
            } else {
                twoPlayerHockeyTable.playStartGameSound()
                binding.pauseMusic2.setCompoundDrawablesWithIntrinsicBounds(null, null, initialDrawable, null)
            }
        }

        binding.exitGame2.setOnClickListener {
            findNavController().popBackStack(R.id.gameFragment, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        soundViewModel.setSoundOn(true)
    }
}