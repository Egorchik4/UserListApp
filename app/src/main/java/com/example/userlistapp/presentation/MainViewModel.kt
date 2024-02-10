package com.example.userlistapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlistapp.domain.entity.ItemEntity
import com.example.userlistapp.domain.usecase.GetItemListUseCase
import com.example.userlistapp.domain.usecase.UpdateItemUseCase
import com.example.userlistapp.presentation.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val getItemListUseCase: GetItemListUseCase,
	private val updateItemUseCase: UpdateItemUseCase
) : ViewModel() {

	private val _listMut = MutableLiveData<State>(State.Initial)
	val listLive: LiveData<State> = _listMut

	fun getContactList() {
		viewModelScope.launch {
			val list = getItemListUseCase()
			_listMut.value = State.Content(listEntity = list)
		}
	}

	fun navigateToFragmentByTag(tag: String) {
		_listMut.value = State.ToFragmentByTag(tag = tag)
	}

	fun editItem(itemEntity: ItemEntity) {
		viewModelScope.launch {
			updateItemUseCase(itemEntity)
		}
	}
}