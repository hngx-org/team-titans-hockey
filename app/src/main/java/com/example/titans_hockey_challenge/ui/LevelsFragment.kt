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
import com.example.titans_hockey_challenge.viewmodels.LevelsDifficultySharedViewModel
import com.example.titans_hockey_challenge.ui.hockeytable.HockeyTable

class LevelsFragment : Fragment() {
    lateinit var binding: FragmentLevelsBinding
    private val levelsViewModel: LevelsDifficultySharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLevelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            easybtn.setOnClickListener {
                levelsViewModel.setEasyLevel()
                findNavController().navigate(R.id.hockeyFragment)

            }

            normBtn.setOnClickListener {
                levelsViewModel.setMediumLevel()
                findNavController().navigate(R.id.hockeyFragment)

            }

            hardBtn.setOnClickListener {
                levelsViewModel.setHardLevel()
                findNavController().navigate(R.id.hockeyFragment)
            }


        }

    }


}