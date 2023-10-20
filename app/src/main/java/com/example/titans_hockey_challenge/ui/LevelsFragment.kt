package com.example.titans_hockey_challenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentLevelsBinding
import com.example.titans_hockey_challenge.models.LevelsDifficultyViewModel

class LevelsFragment : Fragment() {
   lateinit var binding: FragmentLevelsBinding
    private val levelsViewModel: LevelsDifficultyViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLevelsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            bckbtn.setOnClickListener {

                findNavController().navigate(R.id.gameFragment)
            }

            easybtn.setOnClickListener {
                levelsViewModel.setDifficultyLevel(5f) // Easy level
                findNavController().navigate(R.id.gameFragment)
            }

            normBtn.setOnClickListener {
                levelsViewModel.setDifficultyLevel(10f) // Medium level
                findNavController().navigate(R.id.gameFragment)

            }

            hardBtn.setOnClickListener {
                levelsViewModel.setDifficultyLevel(15f) // Hard level
                findNavController().navigate(R.id.gameFragment)


            }


        }

    }



}