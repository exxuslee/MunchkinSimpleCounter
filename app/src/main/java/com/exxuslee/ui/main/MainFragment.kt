package com.exxuslee.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exxuslee.R
import com.exxuslee.databinding.FragmentFirstBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private val sharedPreferences: MyPreferences by inject()
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

        val mainAdapter = MainAdapter(resources.obtainTypedArray(R.array.icons))
        binding.recyclerView.adapter = mainAdapter

        viewModel.players.observe(viewLifecycleOwner) { listPlayers ->
            if (listPlayers != null) {
                val onlinePlayer = listPlayers.filter { player -> player.playing }
                mainAdapter.updateAdapter(onlinePlayer)
            }
        }

        binding.bottomNavigationGame.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.levelPlus -> {
                    viewModel.level(1)
                }
                R.id.levelMinus -> {
                    viewModel.level(-1)
                }
                R.id.bonusPlus -> {
                    viewModel.bonus(1)
                }
                R.id.bonusMinus -> {
                    viewModel.bonus(-1)
                }
                R.id.more -> binding.bottomNavigationSecond.toggleVisibility()
            }
            return@setOnItemSelectedListener true
        }

        binding.bottomNavigationSecond.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.newGame -> {
                    viewModel.newGame()
                    binding.bottomNavigationSecond.toggleVisibility()
                }
                R.id.settings -> findNavController().navigate(R.id.action_main_to_setting)
                R.id.darkMode -> {
                    val mode = !sharedPreferences["DARK_STATUS", false]
                    viewModel.darkMode(mode)
                    if (mode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    sharedPreferences.store("DARK_STATUS", mode)
                }
                R.id.about -> about()
            }
            return@setOnItemSelectedListener true
        }

        mainAdapter.onPlayerClickListener = { position -> viewModel.selectPlayer(position) }
        mainAdapter.onIconClickListener = { position -> viewModel.changeIcon(position) }

        checkTheme()
    }

    private fun checkTheme() {
        val mode = sharedPreferences["DARK_STATUS", false]
        if (mode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
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
        AlertDialog.Builder(context).setTitle("About..").setMessage("Set like in PlayMarket!")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                binding.bottomNavigationSecond.toggleVisibility()
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://www.google.com/")
                startActivity(openURL)
            }.setNegativeButton(android.R.string.cancel) { _, _ ->
                binding.bottomNavigationSecond.toggleVisibility()
            }.setIcon(android.R.drawable.ic_dialog_info).show()
    }
}
