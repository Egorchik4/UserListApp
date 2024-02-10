package com.example.userlistapp.presentation.state

import com.example.userlistapp.domain.entity.ItemEntity

sealed class State {

	data object Initial : State()

	data class Content(val listEntity: List<ItemEntity>) : State()

	data class ToFragmentByTag(val tag: String) : State()
}