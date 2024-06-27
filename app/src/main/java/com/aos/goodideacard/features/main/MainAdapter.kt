package com.aos.goodideacard.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.goodideacard.databinding.AdapterMainBinding
import com.aos.goodideacard.model.CardDeckModel

class MainAdapter: ListAdapter<CardDeckModel, MainAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: AdapterMainBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CardDeckModel) = with(binding) {
            cardViewContent.text = data.content
            cardViewWhose.text = data.subContent
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
        val diffUtil = object : DiffUtil.ItemCallback<CardDeckModel>() {
            override fun areItemsTheSame(oldItem: CardDeckModel, newItem: CardDeckModel): Boolean {
                return oldItem.cardId == newItem.cardId
            }
            override fun areContentsTheSame(oldItem: CardDeckModel, newItem: CardDeckModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}