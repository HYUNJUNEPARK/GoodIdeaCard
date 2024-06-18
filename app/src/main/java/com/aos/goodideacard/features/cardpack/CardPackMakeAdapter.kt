package com.aos.goodideacard.features.cardpack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.databinding.AdapterCardPackBinding

class CardPackMakeAdapter(
    private val onItemClick:(CardPackEntity) -> Unit,
    private val onItemLongClick: (CardPackEntity) -> Unit
): ListAdapter<CardPackEntity, CardPackMakeAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: AdapterCardPackBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CardPackEntity) = with(binding) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPackMakeAdapter.ViewHolder {
        val binding = AdapterCardPackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardPackMakeAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CardPackEntity>() {
            override fun areItemsTheSame(oldItem: CardPackEntity, newItem: CardPackEntity): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: CardPackEntity, newItem: CardPackEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}