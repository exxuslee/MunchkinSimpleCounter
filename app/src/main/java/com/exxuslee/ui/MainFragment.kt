package com.exxuslee.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.exxuslee.R
import com.exxuslee.databinding.FragmentFirstBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment(), MenuProvider {

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
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.loadPlayers()
        val mainAdapter = MainAdapter()
        binding.recyclerView.adapter = mainAdapter
        mainAdapter.tableHeader()

        viewModel.players.observe(viewLifecycleOwner) { Player ->
            mainAdapter.updateAdapter(Player)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.new_game -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            R.id.settings -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            R.id.about -> aboutDialog()
            else -> false
        }
    }

    private fun aboutDialog(): Boolean {
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
        return true
    }
}