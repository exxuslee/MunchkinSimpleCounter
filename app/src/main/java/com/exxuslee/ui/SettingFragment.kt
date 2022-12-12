package com.exxuslee.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
                R.id.addPlayer -> addPlayer()
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

    private fun addPlayer() {
        val view = layoutInflater.inflate(R.layout.add_player,null)
        AlertDialog.Builder(context)
            .setTitle("Add player")
            .setMessage("Set sex and name of player")
            .setView(view)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                Log.d("player", "about $dialog $which")
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }
}