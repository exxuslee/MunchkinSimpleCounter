package com.exxuslee.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.exxuslee.R
import com.exxuslee.core.Dialog
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
                R.id.levelPlus -> viewModel.level(addOne)
                R.id.levelMinus -> viewModel.level(deleteOne)
                R.id.bonusPlus -> viewModel.bonus(addOne)
                R.id.bonusMinus -> viewModel.bonus(deleteOne)
                R.id.newGame -> viewModel.newGame()
                R.id.settings -> findNavController().navigate(R.id.action_main_to_setting)
                R.id.darkMode -> {
                    viewModel.saveMode()
                    viewModel.theme()
                }
                R.id.about -> about()
                else -> throw IllegalArgumentException("Wrong type of menu")
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
        viewModel.theme()
        viewModel.init(savedInstanceState == null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun about() {
        Dialog.Base(context).apply(
            getString(R.string.About),
            getString(R.string.like),
            null
        ){
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(getString(R.string.http_site))
            startActivity(openURL)
        }
    }

    companion object {
        //private const val TAG = "player"
        private const val addOne = 1
        private const val deleteOne = -1
    }
}
