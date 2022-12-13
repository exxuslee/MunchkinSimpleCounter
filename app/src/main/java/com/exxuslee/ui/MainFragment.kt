package com.exxuslee.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exxuslee.R
import com.exxuslee.databinding.FragmentFirstBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadPlayers()

        val mainAdapter = MainAdapter()
        binding.recyclerView.adapter = mainAdapter

        viewModel.players.observe(viewLifecycleOwner) { Player ->
            mainAdapter.updateAdapter(Player)
        }

        binding.bottomNavigationGame.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.levelPlus -> {viewModel.level(1)}
                R.id.levelMinus -> {viewModel.level(-1)}
                R.id.bonusPlus -> {viewModel.bonus(1)}
                R.id.bonusMinus -> {viewModel.bonus(-1)}
                R.id.more -> binding.bottomNavigationSecond.toggleVisibility()
            }
            return@setOnItemSelectedListener true
        }

        binding.bottomNavigationSecond.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.newGame -> {}
                R.id.settings -> {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                R.id.about -> about()
            }
            return@setOnItemSelectedListener true
        }

        mainAdapter.onPlayerClickListener = { position -> viewModel.selectPlayer(position) }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun View.toggleVisibility() {
        if (this.isVisible) this.visibility = View.INVISIBLE
        else this.visibility = View.VISIBLE
    }

    private fun about() {
        AlertDialog.Builder(context)
            .setTitle("About..")
            .setMessage("Set like in PlayMarket!")
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                Log.d("player", "about $dialog $which")
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }
}
