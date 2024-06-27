package com.aos.goodideacard.features.carddeck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.goodideacard.database.enitiy.CardDeckEntity
import com.aos.goodideacard.databinding.AdapterCardPackBinding

class CardPackListAdapter(
    private val onItemClick:(CardDeckEntity) -> Unit,
    private val onItemLongClick: (CardDeckEntity) -> Unit
): ListAdapter<CardDeckEntity, CardPackListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: AdapterCardPackBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CardDeckEntity) = with(binding) {
            adapterCardPackName.text = item.name
            adapterCardPackDescription.text = item.description

            root.setOnClickListener {
                onItemClick(item)
            }

            root.setOnLongClickListener {
                onItemLongClick(item)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPackListAdapter.ViewHolder {
        val binding = AdapterCardPackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardPackListAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CardDeckEntity>() {
            override fun areItemsTheSame(oldItem: CardDeckEntity, newItem: CardDeckEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: CardDeckEntity, newItem: CardDeckEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}