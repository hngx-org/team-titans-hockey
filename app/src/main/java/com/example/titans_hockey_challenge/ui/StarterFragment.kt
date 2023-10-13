package com.example.titans_hockey_challenge.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.databinding.FragmentStarterBinding

class StarterFragment : Fragment() {
    lateinit var binding: FragmentStarterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout using the binding
        binding = FragmentStarterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.anime)

        binding.apply {
            starterFragment.startAnimation(anim)
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.gameFragment)
            }, 6000)
        }
    }
}
