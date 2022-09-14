package com.example.numbersfacts.presentation.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.numbersfacts.R
import com.example.numbersfacts.databinding.FragmentDetailBinding
import com.example.numbersfacts.domain.models.NumberFact
import com.example.numbersfacts.presentation.main.MainActivity
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        initBindUI()
        observers()

        return view
    }

    private fun bindFact(numberFact: NumberFact){
        binding.numberTV.text = numberFact.number.toString()
        binding.factTV.text = numberFact.fact
    }


    private fun initBindUI(){
        viewModel.setFact(args.fact)

        val activity = requireActivity() as MainActivity
        activity.showBackButton(true)
    }

    private fun observers(){
        lifecycleScope.launchWhenStarted {
            viewModel.error.collectLatest {
                Toast.makeText(this@DetailFragment.requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.fact.observe(viewLifecycleOwner){
            bindFact(it)
        }
    }



}