package com.example.userlistapp.ui.contact.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.userlistapp.domain.entity.ItemEntity

class ContactAdapter(
	private val onItemClick: (itemEntity: ItemEntity) -> Unit
) : ListAdapter<ItemEntity, ItemViewHolder>(PaymentsDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ItemViewHolder.from(parent)

	override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
		holder.bind(getItem(position), onItemClick)
	}
}

class PaymentsDiffCallback : DiffUtil.ItemCallback<ItemEntity>() {

	override fun areItemsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: ItemEntity, newItem: ItemEntity): Boolean {
		return oldItem == newItem
	}
}