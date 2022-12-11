package com.exxuslee.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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
        mainAdapter.tableHeader()

        viewModel.players.observe(viewLifecycleOwner) { Player ->
            mainAdapter.updateAdapter(Player)
        }

        binding.bottomNavigationGame.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.levelPlus -> {}
                R.id.levelMinus -> {}
                R.id.bonusPlus -> {}
                R.id.bonusMinus -> {}
                R.id.more -> binding.bottomNavigationSetting.toggleVisibility()
            }
            return@setOnItemSelectedListener true
        }

        binding.bottomNavigationSetting.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.newGame -> {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                R.id.settings -> {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                R.id.about -> about()
            }
            return@setOnItemSelectedListener true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun View.toggleVisibility() {
        if (this.isVisible) {
            this.visibility = View.INVISIBLE
        } else {
            this.visibility = View.VISIBLE
        }
    }

    fun about() {
        AlertDialog.Builder(context)
            .setTitle("About..")
            .setMessage("Set like in PlayMarket!")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    // Continue with delete operation
                })
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }
}
