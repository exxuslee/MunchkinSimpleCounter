package com.exxuslee.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exxuslee.R
import com.exxuslee.databinding.FragmentSecondBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationSecond.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addPlayer -> {}
                R.id.delPlayer -> {}
                R.id.back -> {
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}