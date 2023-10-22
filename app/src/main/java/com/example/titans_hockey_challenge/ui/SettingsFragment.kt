package com.example.titans_hockey_challenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            soundTv.setOnClickListener {

            }

             switchTv.setOnClickListener {

            }

            themeTV.setOnClickListener {
                findNavController().navigate(R.id.customiseFragment)
            }

            howToPlayTV.setOnClickListener {
               findNavController().navigate(R.id.rulesFragment)
            }


            abtTv.setOnClickListener {
                findNavController().navigate(R.id.aboutFragment)

            }
        }
    }
}