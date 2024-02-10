package com.example.userlistapp.data.repository

import com.example.userlistapp.data.datasource.DataSource
import com.example.userlistapp.domain.entity.ItemEntity
import com.example.userlistapp.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataSource: DataSource) : Repository {

	override suspend fun getListItem(): List<ItemEntity> =
		dataSource.getListItem()

	override suspend fun updateItem(itemEntityForUpdate: ItemEntity) {
		dataSource.updateItem(itemEntityForUpdate)
	}
}