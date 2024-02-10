package com.example.userlistapp.domain.usecase

import com.example.userlistapp.domain.entity.ItemEntity
import com.example.userlistapp.domain.repository.Repository
import javax.inject.Inject

class UpdateItemUseCase @Inject constructor(private val repository: Repository) {

	suspend operator fun invoke(item: ItemEntity) {
		repository.updateItem(item)
	}
}