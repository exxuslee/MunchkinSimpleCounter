package com.exxuslee.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exxuslee.R
import com.exxuslee.databinding.FragmentFirstBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainFragmentViewModel by viewModel()
    private val bottomMenu by lazy { BottomMenu.Base(binding.bottomNavigationGame.menu) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init(savedInstanceState == null)

        val mainAdapter = MainAdapter(resources.obtainTypedArray(R.array.icons))
        binding.recyclerView.adapter = mainAdapter


        viewModel.observe(viewLifecycleOwner) { listPlayers ->
            if (listPlayers != null) {
                val onlinePlayer = listPlayers.filter { player -> player.playing }
                mainAdapter.updateAdapter(onlinePlayer)
            }
        }

        binding.bottomNavigationGame.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.levelPlus -> {
                    viewModel.level(ADD_ONE)
                }
                R.id.levelMinus -> {
                    viewModel.level(DEL_ONE)
                }
                R.id.bonusPlus -> {
                    viewModel.bonus(ADD_ONE)
                }
                R.id.bonusMinus -> {
                    viewModel.bonus(DEL_ONE)
                }
                R.id.newGame -> {
                    viewModel.newGame()
//                    binding.bottomNavigationSecond.toggleVisibility()
                }
                R.id.settings -> findNavController().navigate(R.id.action_main_to_setting)
                R.id.darkMode -> {
                    viewModel.saveMode()
                    viewModel.loadMode()
                }
                R.id.about -> about()
            }
            return@setOnItemSelectedListener true
        }


        binding.fab.setOnClickListener {
            binding.bottomNavigationGame.menu.clear()
            binding.bottomNavigationGame.inflateMenu(R.menu.settings)
        }

        mainAdapter.onPlayerClickListener = { position ->
            viewModel.selectPlayer(position)
            bottomMenu.activated()
        }

        mainAdapter.onIconClickListener = { position ->
            viewModel.selectPlayer(position)
            viewModel.changeIcon(position)
        }
        viewModel.loadMode()
        viewModel.init(savedInstanceState == null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun about() {
        AlertDialog.Builder(context).setTitle("About..").setMessage("Set like in PlayMarket!")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://www.google.com/")
                startActivity(openURL)
            }.setNegativeButton(android.R.string.cancel) { _, _ ->
            }.setIcon(android.R.drawable.ic_dialog_info).show()
    }

    companion object {
        const val TAG = "player"
        const val ADD_ONE = 1
        const val DEL_ONE = -1
    }
}
