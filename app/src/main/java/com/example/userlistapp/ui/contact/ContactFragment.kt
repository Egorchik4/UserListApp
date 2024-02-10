package com.example.userlistapp.ui.contact

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.userlistapp.databinding.FragmentContactBinding
import com.example.userlistapp.domain.entity.ItemEntity
import com.example.userlistapp.presentation.MainViewModel
import com.example.userlistapp.presentation.state.State
import com.example.userlistapp.ui.MainActivity
import com.example.userlistapp.ui.MainActivity.Companion.FRAGMENT_RESULT_KEY_TO_CONTACT
import com.example.userlistapp.ui.MainActivity.Companion.FRAGMENT_RESULT_KEY_TO_EDIT
import com.example.userlistapp.ui.contact.adapter.ContactAdapter
import com.example.userlistapp.ui.edit.EditFragment.Companion.FRAGMENT_EDIT_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFragment : Fragment() {

	companion object {

		const val FRAGMENT_CONTACT_TAG = "FRAGMENT_CONTACT_TAG"

		fun newInstance() = ContactFragment()
	}

	private lateinit var binding: FragmentContactBinding
	private val viewModel: MainViewModel by activityViewModels()
	private lateinit var adapter: ContactAdapter
	private var entityForContact: ItemEntity? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentContactBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		Log.e("eee", "onViewCreated CONTACT")
		setResultListener()
		setAdapter()
		setObserver()
		viewModel.getContactList()
	}

	private fun setResultListener() {
		setFragmentResultListener(requestKey = FRAGMENT_RESULT_KEY_TO_CONTACT, listener = { _, bundle ->
			entityForContact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				bundle.getParcelable(MainActivity.ITEM_ENTITY_KEY, ItemEntity::class.java)
			} else {
				@Suppress("DEPRECATION") bundle.getParcelable(MainActivity.ITEM_ENTITY_KEY)
			}
			entityForContact?.let {
				viewModel.editItem(it)
			}
		})
	}

	private fun setAdapter() {
		adapter = ContactAdapter(::navigateToEditFragment)
		binding.rcView.adapter = adapter
	}

	private fun navigateToEditFragment(itemEntity: ItemEntity) {
		setFragmentResult(FRAGMENT_RESULT_KEY_TO_EDIT, bundleOf(MainActivity.ITEM_ENTITY_KEY to itemEntity))
		viewModel.navigateToFragmentByTag(tag = FRAGMENT_EDIT_TAG)
	}

	private fun setObserver() {
		viewModel.listLive.observe(viewLifecycleOwner) {
			when (it) {
				State.Initial, is State.ToFragmentByTag -> {}

				is State.Content                        -> {
					handleContentState(it)
				}
			}
		}
	}

	private fun handleContentState(it: State.Content) {
		adapter.submitList(it.listEntity)
	}
}