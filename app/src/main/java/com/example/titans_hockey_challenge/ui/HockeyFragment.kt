package com.example.titans_hockey_challenge.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentHockeyBinding
import com.example.titans_hockey_challenge.utils.GameThread

class HockeyFragment : Fragment() {
    private var mGameThread: GameThread? = null

    private var _binding: FragmentHockeyBinding? = null
    private val binding get() = _binding !!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentHockeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hockeyTable = binding.hockeyTable
        hockeyTable.setScoreOpponent(binding.tvScoreOpponent)
        hockeyTable.setScorePlayer(binding.tvScorePlayer)
        hockeyTable.setStatus(binding.tvStatus)

        binding.imgPauseButton.setOnClickListener {
            hockeyTable.pauseGame()
        }

        mGameThread = hockeyTable.game
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}