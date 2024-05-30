package com.aos.goodideacard.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.goodideacard.databinding.AdapterCardItemBinding
import com.aos.goodideacard.database.enitiy.CombinedCardItem

class CardItemAdapter: ListAdapter<CombinedCardItem, CardItemAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: AdapterCardItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CombinedCardItem) = with(binding) {
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
        val diffUtil = object : DiffUtil.ItemCallback<CombinedCardItem>() {
            override fun areItemsTheSame(oldItem: CombinedCardItem, newItem: CombinedCardItem): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: CombinedCardItem, newItem: CombinedCardItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}