package com.example.feature.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.example.common.utils.observe
import com.example.feature.currencylist.adapter.CurrencyListAdapter
import com.example.feature.currencylist.databinding.FragmentCurrencyListBinding
import com.example.feature.currencylist.viewmodel.CurrencyListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment: Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null
    private val binding get() = _binding!!

    private val adapter: CurrencyListAdapter by lazy {
        CurrencyListAdapter()
    }

    private val viewModel: CurrencyListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyListBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleInsets()

        binding.rvList.adapter = adapter

        startObserve()
    }

    private fun startObserve() {
        viewModel.originalListFlow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun handleInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.rvList) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                top = insets.top,
                bottom = insets.bottom
            )

            windowInsets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}