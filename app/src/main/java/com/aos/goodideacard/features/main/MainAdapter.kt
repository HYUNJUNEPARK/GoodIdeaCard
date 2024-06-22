package com.aos.goodideacard.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.goodideacard.databinding.AdapterMainBinding
import com.aos.goodideacard.model.CardPackModel

class MainAdapter: ListAdapter<CardPackModel, MainAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: AdapterMainBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CardPackModel) = with(binding) {
            cardViewContent.text = data.content
            cardViewWhose.text = data.whose
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CardPackModel>() {
            override fun areItemsTheSame(oldItem: CardPackModel, newItem: CardPackModel): Boolean {
                return oldItem.cardId == newItem.cardId
            }
            override fun areContentsTheSame(oldItem: CardPackModel, newItem: CardPackModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}