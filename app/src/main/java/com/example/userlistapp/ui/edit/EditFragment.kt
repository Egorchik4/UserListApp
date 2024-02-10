package com.example.userlistapp.ui.edit

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.userlistapp.R
import com.example.userlistapp.databinding.FragmentEditBinding
import com.example.userlistapp.domain.entity.ItemEntity
import com.example.userlistapp.presentation.MainViewModel
import com.example.userlistapp.ui.MainActivity.Companion.FRAGMENT_RESULT_KEY_TO_CONTACT
import com.example.userlistapp.ui.MainActivity.Companion.FRAGMENT_RESULT_KEY_TO_EDIT
import com.example.userlistapp.ui.MainActivity.Companion.ITEM_ENTITY_KEY
import com.example.userlistapp.ui.contact.ContactFragment.Companion.FRAGMENT_CONTACT_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : Fragment() {

	companion object {

		const val FRAGMENT_EDIT_TAG = "FRAGMENT_EDIT_TAG"

		fun newInstance() = EditFragment()
	}

	private lateinit var binding: FragmentEditBinding
	private val viewModel: MainViewModel by activityViewModels()
	private var entityForEdit: ItemEntity? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentEditBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setResultListener()
		setListener()
	}

	private fun setResultListener() {
		setFragmentResultListener(requestKey = FRAGMENT_RESULT_KEY_TO_EDIT, listener = { _, bundle ->
			entityForEdit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				bundle.getParcelable(ITEM_ENTITY_KEY, ItemEntity::class.java)
			} else {
				@Suppress("DEPRECATION") bundle.getParcelable(ITEM_ENTITY_KEY)
			}
			entityForEdit?.let {
				with(binding) {
					Glide.with(imageAvatar)
						.load(it.photoURL)
						.circleCrop()
						.placeholder(R.mipmap.ic_launcher)
						.diskCacheStrategy(DiskCacheStrategy.NONE)
						.into(imageAvatar)
					editTextName.setText(it.name)
					editTextSecondName.setText(it.secondName)
					editTextNumberOfPhone.setText(it.numberOfPhone)
					editTextImageUrl.setText(it.photoURL)
				}
			}
		})
	}

	private fun setListener() {
		with(binding) {
			buttonBack.setOnClickListener {
				entityForEdit?.let {
					val newEntity = ItemEntity(
						id = it.id,
						name = editTextName.text.toString(),
						secondName = editTextSecondName.text.toString(),
						numberOfPhone = editTextNumberOfPhone.text.toString(),
						photoURL = editTextImageUrl.text.toString()
					)
					navigateToContactFragment(newEntity)
				}
			}
			editTextImageUrl.addTextChangedListener {
				if (editTextImageUrl.isFocused) {
					Log.e("eee", "addTextChangedListener  ${it}")
					Glide.with(imageAvatar)
						.load(it.toString())
						.circleCrop()
						.placeholder(R.mipmap.ic_launcher)
						.diskCacheStrategy(DiskCacheStrategy.NONE)
						.into(imageAvatar)
				}
			}
		}
	}

	private fun navigateToContactFragment(itemEntity: ItemEntity) {
		setFragmentResult(FRAGMENT_RESULT_KEY_TO_CONTACT, bundleOf(ITEM_ENTITY_KEY to itemEntity))
		viewModel.navigateToFragmentByTag(tag = FRAGMENT_CONTACT_TAG)
	}
}