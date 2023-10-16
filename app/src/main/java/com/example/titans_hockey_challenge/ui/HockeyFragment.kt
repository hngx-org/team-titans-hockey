package com.example.titans_hockey_challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.titans_hockey_challenge.databinding.FragmentHockeyBinding
import com.example.titans_hockey_challenge.models.HockeyTable
import com.example.titans_hockey_challenge.utils.GameThread
import com.example.titans_hockey_challenge.utils.STATE_RUNNING

class HockeyFragment : Fragment() {
    private var mGameThread: GameThread? = null
    private lateinit var hockeyTable: HockeyTable

    private var _binding: FragmentHockeyBinding? = null
    private val binding get() = _binding !!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentHockeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hockeyTable = binding.hockeyTable
        hockeyTable.setScoreOpponent(binding.tvScoreOpponent)
        hockeyTable.setScorePlayer(binding.tvScorePlayer)
        hockeyTable.setStatus(binding.tvStatus)

        mGameThread = hockeyTable.game

        binding.imgPauseButton.setOnClickListener {
            if (mGameThread?.isBetweenRounds == true) {
                mGameThread?.setState(STATE_RUNNING)
                hockeyTable.playStartGameSound()
            } else {
                if (mGameThread?.mRun == true) {
                    // Pause the game
                    mGameThread?.setRunning(false)
                    hockeyTable.pauseBackgroundSound()
                    binding.overlay.visibility = View.VISIBLE
                } else {
                    // Resume the game
                    mGameThread?.setRunning(true)
                    hockeyTable.playStartGameSound()
                    binding.overlay.visibility = View.GONE
                }
            }
        }

       binding.resumeGame.setOnClickListener {
            mGameThread?.setState(STATE_RUNNING)
            hockeyTable.playStartGameSound()
            binding.overlay.visibility = View.GONE
        }

        binding.exitGame.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}