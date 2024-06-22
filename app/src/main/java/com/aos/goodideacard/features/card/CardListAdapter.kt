package com.aos.goodideacard.features.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.goodideacard.database.enitiy.CardEntity
import com.aos.goodideacard.databinding.AdapterCardListBinding

class CardListAdapter: ListAdapter<CardEntity, CardListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: AdapterCardListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CardEntity) = with(binding) {
            cardViewContent.text = data.content
            cardViewSubContent.text = data.subContent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CardEntity>() {
            override fun areItemsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
                return oldItem.cardId == newItem.cardId
            }
            override fun areContentsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}