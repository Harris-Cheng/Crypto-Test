package com.example.feature.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.core.widget.doOnTextChanged
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
        setupOnClick()
        setupSearch()
    }

    private fun setupOnClick() {
        binding.mainButton.setOnClickListener {
            viewModel.onClearAllClick()
        }
        binding.button1.setOnClickListener {
            viewModel.onInsertAllClick()
        }
        binding.button2.setOnClickListener {
            viewModel.onShowListAClick()
        }
        binding.button3.setOnClickListener {
            viewModel.onShowListBClick()
        }
        binding.button4.setOnClickListener {
            viewModel.onShowAllClick()
        }
    }

    private fun setupSearch() {
        with(binding.edtSearch) {
            doOnTextChanged { text, _, _, _ ->
                viewModel.searchTerm(text.toString())
            }
        }
    }

    private fun startObserve() {
        viewModel.displayList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.isEmptyList.observe(viewLifecycleOwner) {
            binding.emptyLayout.root.isVisible = it
        }
    }

    private fun handleInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.rvList) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            binding.root.updatePadding(
                top = insets.top
            )

            binding.buttonContainer.updateLayoutParams<MarginLayoutParams> {
                updateMargins(
                    bottom = insets.bottom
                )
            }
            binding.buttonContainer.doOnLayout {
                v.updatePadding(
                    bottom = insets.bottom + it.height
                )
            }

            windowInsets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}