package com.example.titans_hockey_challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.titans_hockey_challenge.databinding.FragmentHockeyBinding
import com.example.titans_hockey_challenge.utils.GameThread

class HockeyFragment : Fragment() {
    private var mGameThread: GameThread? = null
    private lateinit var binding: FragmentHockeyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHockeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hockeyTable = binding.hockeyTable
        hockeyTable.setScoreOpponent(binding.tvScoreOpponent)
        hockeyTable.setScorePlayer(binding.tvScorePlayer)
        hockeyTable.setStatus(binding.tvStatus)

        mGameThread = hockeyTable.game
    }
}