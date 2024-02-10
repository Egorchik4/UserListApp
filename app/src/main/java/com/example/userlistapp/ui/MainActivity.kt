package com.example.userlistapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.userlistapp.R
import com.example.userlistapp.databinding.ActivityMainBinding
import com.example.userlistapp.presentation.MainViewModel
import com.example.userlistapp.presentation.state.State
import com.example.userlistapp.ui.contact.ContactFragment
import com.example.userlistapp.ui.contact.ContactFragment.Companion.FRAGMENT_CONTACT_TAG
import com.example.userlistapp.ui.edit.EditFragment
import com.example.userlistapp.ui.edit.EditFragment.Companion.FRAGMENT_EDIT_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

	companion object {

		const val FRAGMENT_RESULT_KEY_TO_EDIT = "FRAGMENT_RESULT_KEY_TO_EDIT"
		const val FRAGMENT_RESULT_KEY_TO_CONTACT = "FRAGMENT_RESULT_KEY_TO_CONTACT"
		const val ITEM_ENTITY_KEY = "ITEM_ENTITY_KEY"
	}

	private lateinit var binding: ActivityMainBinding
	private val viewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setObserver()
		if (supportFragmentManager.backStackEntryCount == 0) {
			viewModel.navigateToFragmentByTag(tag = FRAGMENT_CONTACT_TAG)
		}
	}

	private fun setObserver() {
		viewModel.listLive.observe(this) {
			when (it) {
				State.Initial, is State.Content -> {}

				is State.ToFragmentByTag        -> {
					navigateToFragmentByTag(tag = it.tag)
				}
			}
		}
	}

	private fun navigateToFragmentByTag(tag: String) {
		with(supportFragmentManager) {
			if (findFragmentByTag(tag) == null) {
				commit {
					tag.toFragment()?.let { replace(R.id.fragmentContainer, it, tag) }
					addToBackStack(tag)
				}
			} else {
				popBackStack(tag, 0)
			}
		}
	}

	@Deprecated("Deprecated in Java")
	override fun onBackPressed() {
		with(supportFragmentManager) {
			if (backStackEntryCount > 1) {
				val tag = supportFragmentManager.fragments.last().tag
				tag?.let { viewModel.navigateToFragmentByTag(it) }
				super.onBackPressed()
			} else {
				finish()
			}
		}
	}

	private fun String.toFragment(): Fragment? {
		return if (this == FRAGMENT_CONTACT_TAG) {
			ContactFragment.newInstance()
		} else if (this == FRAGMENT_EDIT_TAG) {
			EditFragment.newInstance()
		} else {
			null
		}
	}
}