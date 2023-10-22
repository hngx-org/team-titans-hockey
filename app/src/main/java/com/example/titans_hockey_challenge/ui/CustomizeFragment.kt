package com.example.titans_hockey_challenge.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.titans_hockey_challenge.R
import com.example.titans_hockey_challenge.ThemeViewModel
import com.example.titans_hockey_challenge.databinding.FragmentCustomizeBinding
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorSwatch

class CustomizeFragment : Fragment() {
    private lateinit var binding: FragmentCustomizeBinding
    private val themeViewModel: ThemeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themeViewModel.puckColor.observe(viewLifecycleOwner) { color ->
            binding.puckButton.setBackgroundColor(color)
        }

        themeViewModel.paddleColor.observe(viewLifecycleOwner) { color ->
            binding.paddleButton.setBackgroundColor(color)
        }

        binding.puckButton.setOnClickListener { button ->
            showColorDialog(button, themeViewModel::setPuckColor)
        }

        binding.paddleButton.setOnClickListener { button ->
            showColorDialog(button, themeViewModel::setPaddleColor)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showColorDialog(button: View, colorSetter: (Int) -> Unit) {
        MaterialColorPickerDialog
            .Builder(requireContext())
            .setColorSwatch(ColorSwatch._300)
            .setDefaultColor(R.color.player_color)
            .setColorRes(resources.getIntArray(R.array.themeColors))
            .setColorListener(object : ColorListener {
                override fun onColorSelected(color: Int, colorHex: String) {
                    when (button) {
                        binding.puckButton -> {
                            colorSetter(color)
                        }
                        binding.paddleButton -> {
                            colorSetter(color)
                        }
                    }
                }
            })
            .setDismissListener {
                Log.d("ColorPicker", "Handle dismiss event")
            }
            .show()
    }
}