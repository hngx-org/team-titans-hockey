package com.example.titans_hockey_challenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.databinding.FragmentStarterBinding

class starterFragment : Fragment() {
  lateinit var binding: FragmentStarterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentStarterBinding.inflate(inflater,container,false)
        return inflater.inflate(R.layout.fragment_starter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            findNavController().popBackStack()
        }

    }
}