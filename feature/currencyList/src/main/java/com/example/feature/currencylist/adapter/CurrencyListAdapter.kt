package com.example.feature.currencylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.CurrencyModel
import com.example.feature.currencylist.databinding.ItemCurrencyViewBinding

class CurrencyListAdapter: ListAdapter<CurrencyModel, CurrencyViewHolder>(CurrencyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemCurrencyViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(
            getItem(position)
        )
    }
}

class CurrencyViewHolder(
    private val binding: ItemCurrencyViewBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CurrencyModel) {
        binding.icon.text = item.shortName.take(1)
        binding.tvName.text = item.name
        binding.tvShortName.text = item.shortName
    }
}

class CurrencyDiffCallback: DiffUtil.ItemCallback<CurrencyModel>() {
    override fun areItemsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CurrencyModel, newItem: CurrencyModel): Boolean {
        return oldItem == newItem
    }

}