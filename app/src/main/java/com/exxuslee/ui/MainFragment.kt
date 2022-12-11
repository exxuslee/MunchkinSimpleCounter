package com.exxuslee.ui

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

        binding.bottomNavigationSetting.setOnItemSelectedListener {
                item ->
            when(item.itemId) {
                R.id.levelPlus -> {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                R.id.levelMinus -> {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                R.id.bonusPlus -> {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                R.id.bonusMinus -> {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
                R.id.more -> {
                    binding.bottomNavigationSetting2.toggleVisibility()
                }
            }
            return@setOnItemSelectedListener true
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
            R.id.levelPlus -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            else -> false
        }
    }

    fun View.toggleVisibility() {
        if (this.isVisible) {
            this.visibility = View.INVISIBLE
        } else {
            this.visibility = View.VISIBLE
        }
    }
}


//R.id.new_game -> {
//    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//    true
//}
//R.id.settings -> {
//    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//    true
//}
//R.id.about -> {
//    AlertDialog.Builder(context)
//        .setTitle("About..")
//        .setMessage("Set like in PlayMarket!")
//        .setPositiveButton(android.R.string.ok,
//            DialogInterface.OnClickListener { dialog, which ->
//                // Continue with delete operation
//            })
//        .setNegativeButton(android.R.string.cancel, null)
//        .setIcon(android.R.drawable.ic_dialog_info)
//        .show()
//    true
//}