package com.example.userlistapp.domain.repository

import com.example.userlistapp.domain.entity.ItemEntity

interface Repository {

	suspend fun getListItem(): List<ItemEntity>

	suspend fun updateItem(itemEntityForUpdate: ItemEntity)
}