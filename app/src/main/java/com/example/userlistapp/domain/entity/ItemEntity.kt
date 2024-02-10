package com.example.userlistapp.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemEntity(
	val id: Int = 0,
	var name: String,
	var secondName: String,
	var numberOfPhone: String,
	var photoURL: String
) : Parcelable