package com.example.userlistapp.ui.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.userlistapp.R
import com.example.userlistapp.databinding.ItemListBinding
import com.example.userlistapp.domain.entity.ItemEntity

class ItemViewHolder(
	private val binding: ItemListBinding
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		fun from(parent: ViewGroup): ItemViewHolder {
			val inflater = LayoutInflater.from(parent.context)
			val binding = ItemListBinding.inflate(inflater, parent, false)
			return ItemViewHolder(binding)
		}
	}

	fun bind(
		itemEntity: ItemEntity,
		onItemClick: (itemEntity: ItemEntity) -> Unit
	) {
		with(binding) {
			Glide.with(itemView)
				.load(itemEntity.photoURL)
				.circleCrop()
				.placeholder(R.mipmap.ic_launcher)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(imageView)
			textName.text = itemEntity.name
			textSecondName.text = itemEntity.secondName
			textNumberOfPhone.text = itemEntity.numberOfPhone
			root.setOnClickListener { onItemClick(itemEntity) }
		}
	}
}