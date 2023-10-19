package com.example.titans_hockey_challenge.ui.twoplayertable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentTwoPlayerBinding
import com.example.titans_hockey_challenge.utils.TwoPlayerGameThread

class TwoPlayerHockeyFragment : Fragment() {
    private var _binding : FragmentTwoPlayerBinding? = null
    private val binding get() = _binding !!

    private var mTwoPlayerGameThread: TwoPlayerGameThread? = null
    private lateinit var twoPlayerHockeyTable: TwoPlayerHockeyTable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTwoPlayerBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        twoPlayerHockeyTable = binding.twoPlayerHockeyTable
        twoPlayerHockeyTable.setScorePlayer1(binding.tvScorePlayer1)
        twoPlayerHockeyTable.setScorePlayer2(binding.tvScorePlayer2)
        twoPlayerHockeyTable.setStatus(binding.tvStatus2)

        binding.imgPauseButton2.setOnClickListener {
            mTwoPlayerGameThread?._pause()
            twoPlayerHockeyTable.pauseBackgroundSound()
            binding.pauseOverlay2.visibility = View.VISIBLE
        }

        binding.resumeGame2.setOnClickListener {
            mTwoPlayerGameThread?._resume()
            twoPlayerHockeyTable.resumeGame()
            twoPlayerHockeyTable.playStartGameSound()
            binding.pauseOverlay2.visibility = View.GONE
        }

        binding.exitGame2.setOnClickListener {
            findNavController().popBackStack(R.id.gameFragment, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}