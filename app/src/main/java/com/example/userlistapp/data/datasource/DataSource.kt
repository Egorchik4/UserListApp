package com.example.userlistapp.data.datasource

import com.example.userlistapp.domain.entity.ItemEntity
import javax.inject.Inject

class DataSourceImpl @Inject constructor() : DataSource {

	override suspend fun getListItem(): List<ItemEntity> =
		listItem

	override suspend fun updateItem(itemEntityForUpdate: ItemEntity) {
		val index = listItem.indexOfFirst { it.id == itemEntityForUpdate.id }
		listItem[index] = itemEntityForUpdate
	}

	private val listItem: MutableList<ItemEntity> = mutableListOf()

	private fun initData() {
		for (i in 0..3) {
			listItem.add(
				i, ItemEntity(
					id = i,
					name = names.random(),
					secondName = secondNames.random(),
					numberOfPhone = createNumberOfPhone(),
					photoURL = photoURL.random()
				)
			)
		}
	}

	private fun createNumberOfPhone() = buildString {
		setLength(0)
		append("+")
		for (n in 0..9) {
			append((0..9).random().toString())
		}
	}

	private val names: List<String> = listOf(
		"Август",
		"Авраам",
		"Адам",
		"Адриан",
		"Айдар",
		"Аким",
		"Алан",
		"Алихан",
		"Альберт",
		"Антип",
		"Амур",
		"Амир",
		"Аристарх",
		"Арай",
		"Арман",
		"Арсен",
		"Арсений",
		"Артур",
		"Архип",
		"Аскольд",
		"Богдан",
		"Борислав",
		"Бруно",
		"Булат",
		"Вальтер",
		"Вахтанг",
		"Вениамин",
		"Венцеслав",
		"Винсент",
		"Вилльям",
		"Влас",
		"Всеволод",
		"Гавриил",
		"Генри",
		"Гарри",
		"Геракл",
		"Герман",
		"Глеб",
		"Гордей"
	)

	private val secondNames: List<String> = listOf(
		"Авдеев",
		"Агапов",
		"Агафонов",
		"Агеев",
		"Антонов",
		"Артамонов",
		"Артемов",
		"Артемьев",
		"Архипов",
		"Астафьев",
		"Астахов",
		"Афанасьев",
		"Бабушкин",
		"Баженов",
		"Балашов",
		"Баранов",
		"Барсуков",
		"Басов",
		"Безруков",
		"Беликов",
		"Белкин",
		"Белов",
		"Белозёров",
		"Винокуров",
		"Вишневский",
		"Вишняков",
		"Владимиров",
		"Власов",
		"Волков",
		"Волошин",
		"Воробьев",
		"Воронин",
		"Воронков",
		"Воронов",
		"Воронцов",
		"Высоцкий",
		"Гаврилов",
		"Галкин",
		"Герасимов",
		"Гладков",
		"Глебов",
		"Глухов",
		"Глушков",
		"Голиков",
		"Голованов",
		"Гришин",
		"Громов",
	)

	private val photoURL: List<String> = listOf(
		"https://gravatar.com/avatar/cd794dd4d6ef663db00a332cb37ac6fd?s=400&d=robohash&r=x",
		"https://gravatar.com/avatar/cd794dd4d6ef663db00a332cb37ac6fd?s=400&d=monsterid&r=x",
		"https://gravatar.com/avatar/cd794dd4d6ef663db00a332cb37ac6fd?s=400&d=wavatar&r=x",
		"https://gravatar.com/avatar/cd794dd4d6ef663db00a332cb37ac6fd?s=400&d=retro&r=x",
		"https://robohash.org/cd794dd4d6ef663db00a332cb37ac6fd?set=set4&bgset=&size=400x400",
		"https://robohash.org/cd794dd4d6ef663db00a332cb37ac6fd?set=set2&bgset=&size=400x400",
	)

	init {
		initData()
	}
}

interface DataSource {

	suspend fun getListItem(): List<ItemEntity>

	suspend fun updateItem(itemEntityForUpdate: ItemEntity)
}