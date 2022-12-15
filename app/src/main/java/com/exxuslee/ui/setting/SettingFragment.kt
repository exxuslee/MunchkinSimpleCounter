package com.exxuslee.ui.setting

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exxuslee.R
import com.exxuslee.databinding.FragmentSecondBinding
import com.exxuslee.domain.model.Player
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingFragmentViewModel by viewModel()
    private val icons by lazy { resources.obtainTypedArray(R.array.icons) }
    private val textArray by lazy { resources.getStringArray(R.array.icons_string) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadPlayers()

        val settingAdapter = SettingAdapter(resources.obtainTypedArray(R.array.icons))
        binding.recyclerSettingPlayer.adapter = settingAdapter

        viewModel.players.observe(viewLifecycleOwner) { listPlayers ->
            if (listPlayers != null) settingAdapter.updateAdapter(listPlayers)
        }

        binding.bottomNavigationSecond.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addPlayer -> addPlayer()
                R.id.delPlayer -> deletePlayer()
                R.id.back -> {
                    findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                }
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun deletePlayer() {
        val view = layoutInflater.inflate(R.layout.delete_player, null)
        val spinner = view.findViewById(R.id.spinnerName) as Spinner
        val adapter = SpinnerAdapter(
            requireContext(),
            R.layout.add_spinner_icon,
            textArray,
            icons
        )
        spinner.adapter = adapter

        AlertDialog.Builder(context)
            .setTitle("Delete player")
            .setView(view)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                Log.d("player", "about $dialog $which")
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addPlayer() {
        val view = layoutInflater.inflate(R.layout.add_player, null)
        val editName = view.findViewById<View>(R.id.editTextPersonName) as TextView
        val spinner = view.findViewById(R.id.spinnerIcon) as Spinner

        val adapter = SpinnerAdapter(
            requireContext(),
            R.layout.add_spinner_icon,
            textArray,
            icons
        )
        spinner.adapter = adapter

        AlertDialog.Builder(context)
            .setTitle("Add player")
            .setMessage("Set sex and name of player")
            .setView(view)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                viewModel.savePlayer(
                    Player(
                        name = editName.text.toString(),
                        icon = spinner.selectedItemPosition
                    )
                )
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()

    }
}