package com.example.numbersfacts.presentation.search_history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.numbersfacts.databinding.FragmentSearchHistoryBinding
import com.example.numbersfacts.presentation.main.MainActivity
import com.example.numbersfacts.presentation.search_history.adapter.HistoryAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = SearchHistoryFragment()
    }

    private val viewModel: SearchHistoryViewModel by viewModels()
    private var _binding: FragmentSearchHistoryBinding? = null
    private var historyAdapter : HistoryAdapter? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        observers()
        initBindUI()
        return view
    }


    private fun setRecyclerInset(view: View){
        ViewCompat.setOnApplyWindowInsetsListener(view) { insetView, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED

        }
    }

    private fun initBindUI(){
        binding.historyRecycler.adapter = historyAdapter
        binding.historyRecycler.layoutManager = LinearLayoutManager(context)
        //binding.historyRecycler.addItemDecoration(SpacerItemDecorator(5))
        setRecyclerInset(binding.historyRecycler)
        val activity = requireActivity() as MainActivity
        activity.showBackButton(false)
        binding.getFactButton.setOnClickListener{
            viewModel.onEvent(SearchHistoryEvent.GetFactButtonClick)
        }
        binding.getRandomFactButton.setOnClickListener{
            viewModel.onEvent(SearchHistoryEvent.GetRandomFactButtonClick)
        }
        binding.numberTextInputEditText.doAfterTextChanged {
            viewModel.onEvent(SearchHistoryEvent.NumberChanged(if(it.toString() != "") it.toString().toInt() else null))
        }
        val sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        sheetBehavior.isHideable = false
    }

    private fun observers(){
        viewModel.history.observe(viewLifecycleOwner) {
            historyAdapter = HistoryAdapter(it.reversed())
            binding.historyRecycler.adapter = historyAdapter
            binding.historyRecycler.layoutManager = LinearLayoutManager(context)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.getFactButton.isClickable = !it
            binding.getRandomFactButton.isClickable = !it
            binding.historyRecycler.isClickable = !it
        }

        lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest {
                Toast.makeText(this@SearchHistoryFragment.requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.navigation.onEach {
            when(it){
                is SearchHistoryEvent.NavigateToDetailScreen -> {
                    val action = SearchHistoryFragmentDirections.actionSearchHistoryFragmentToDetailFragment(it.numberFact)
                    findNavController().navigate(action)

                }

                else -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }

    override fun onResume() {
        super.onResume()
        viewModel.getHistory()
    }



}