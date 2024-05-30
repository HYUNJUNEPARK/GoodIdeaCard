package com.aos.goodideacard.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.goodideacard.databinding.AdapterCardItemBinding
import com.aos.goodideacard.database.enitiy.MergedCardDeckItem

class CardItemAdapter: ListAdapter<MergedCardDeckItem, CardItemAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: AdapterCardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MergedCardDeckItem) = with(binding) {
            cardViewContent.text = data.embeddedCardEntity.content
            cardViewWhose.text = data.embeddedCardEntity.whose
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
        val diffUtil = object : DiffUtil.ItemCallback<MergedCardDeckItem>() {
            override fun areItemsTheSame(oldItem: MergedCardDeckItem, newItem: MergedCardDeckItem): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MergedCardDeckItem, newItem: MergedCardDeckItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}