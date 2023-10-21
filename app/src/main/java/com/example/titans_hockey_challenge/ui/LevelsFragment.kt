package com.example.titans_hockey_challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentLevelsBinding
import com.example.titans_hockey_challenge.models.HockeyTable
import com.example.titans_hockey_challenge.models.LevelsDifficultyViewModel

class LevelsFragment : Fragment() {
    lateinit var binding: FragmentLevelsBinding
    private val levelsViewModel: LevelsDifficultyViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLevelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hockeyTable = requireActivity().findViewById<HockeyTable>(R.id.hockeyTable)

        binding.apply {

            bckbtn.setOnClickListener {
                findNavController().navigate(R.id.gameFragment)
            }

            levelsViewModel.gameDifficulty.value?.let { difficulty ->
                hockeyTable?.updateAISpeed(difficulty)
            }


            easybtn.setOnClickListener {
                levelsViewModel.setDifficultyLevel(30f) // Easy level
                findNavController().navigate(R.id.hockeyFragment)

            }

            normBtn.setOnClickListener {
                levelsViewModel.setDifficultyLevel(50f) // Medium level
                findNavController().navigate(R.id.hockeyFragment)

            }

            hardBtn.setOnClickListener {
                levelsViewModel.setDifficultyLevel(100f) // Hard level
                findNavController().navigate(R.id.hockeyFragment)
            }


        }

    }


}