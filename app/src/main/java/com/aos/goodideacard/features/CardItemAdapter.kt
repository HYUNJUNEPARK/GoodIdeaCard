package com.aos.goodideacard.features

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.goodideacard.databinding.AdapterCardItemBinding
import com.aos.goodideacard.model.CardItem

class CardItemAdapter: ListAdapter<CardItem, CardItemAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: AdapterCardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CardItem) = with(binding) {
            cardViewContent.text = data.content
            cardViewWhose.text = data.whose
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CardItem>() {
            override fun areItemsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: CardItem, newItem: CardItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}