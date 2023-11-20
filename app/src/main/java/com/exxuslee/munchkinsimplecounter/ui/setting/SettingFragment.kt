package com.exxuslee.munchkinsimplecounter.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.core.Dialog
import com.exxuslee.munchkinsimplecounter.core.vibratePhone
import com.exxuslee.munchkinsimplecounter.databinding.FragmentSecondBinding
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

        viewModel.observe(viewLifecycleOwner) { listPlayers ->
            if (listPlayers != null) settingAdapter.updateAdapter(listPlayers)
        }

        binding.bottomNavigationSecond.setOnItemSelectedListener { item ->
            vibratePhone()
            when (item.itemId) {
                R.id.addPlayer -> addPlayer()
                R.id.delPlayer -> checkDelete()
                else -> throw IllegalArgumentException("Wrong type of menu")
            }
            return@setOnItemSelectedListener true
        }

        settingAdapter.onCheckClickListener = { position ->
            vibratePhone()
            viewModel.onlinePlayer(position)
        }

        binding.fab.setOnClickListener {
            vibratePhone()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun checkDelete() {
        if (viewModel.communication.value().isNotEmpty()) deletePlayer()
        else Toast.makeText(requireContext(), getString(R.string.empty_players), Toast.LENGTH_SHORT)
            .show()
    }

    private fun deletePlayer() {
        val view = layoutInflater.inflate(R.layout.delete_player, null)
        val spinner = view.findViewById(R.id.spinnerName) as Spinner
        val adapter = SpinnerAdapterDel(
            requireContext(),
            R.layout.delete_player_spinner,
            R.id.spinnerTextView,
            viewModel.communication.value(),
            icons,
        )
        spinner.adapter = adapter

        Dialog.Base(context).apply(
            getString(R.string.delete_player),
            "",
            view
        ) { viewModel.deletePlayer(spinner.selectedItemPosition) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addPlayer() {
        val view = layoutInflater.inflate(R.layout.add_player, null)
        val editName = view.findViewById<View>(R.id.editTextPersonName) as TextView
        val spinner = view.findViewById(R.id.spinnerIcon) as Spinner

        val adapter = SpinnerAdapterAdd(
            requireContext(), R.layout.add_spinner_icon, R.id.spinnerTextView, textArray, icons
        )
        spinner.adapter = adapter

        Dialog.Base(context).apply(
            getString(R.string.add_player),
            getString(R.string.set_sex_name_player),
            view
        ) {
            viewModel.savePlayer(
                Player(
                    name = editName.text.toString(),
                    icon = spinner.selectedItemPosition
                )
            )
        }

    }
}