package com.makaroni.chocho.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.makaroni.chocho.MainActivityKt
import com.makaroni.chocho.R
import com.makaroni.chocho.databinding.FragmentCollectionBinding
import java.lang.RuntimeException


class CollectionFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentCollectionBinding

    private val viewModel: CollectionViewModel by viewModels()

    private var pagerAdapter: CollectionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentCollectionBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToolbar()
        subscribeUi()
        subscribeViewPager()
        navigateToAuth()
    }

    private fun subscribeUi() {
        fragmentBinding.apply {
            fabAdd.setOnClickListener { navigateToEditor() }
        }
    }

    private fun subscribeToolbar() {
        val mainActivity = requireActivity() as MainActivityKt
        mainActivity.subscribeToolbarLeadingButton(this, fragmentBinding.toolbar)
        fragmentBinding.toolbar.apply {
            inflateMenu(R.menu.menu_collection)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_search -> navigateToSearch()
                }
                true
            }
        }
    }

    private fun subscribeViewPager() {
        pagerAdapter = CollectionAdapter()
        fragmentBinding.viewPager.adapter = pagerAdapter
        fragmentBinding.apply {
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                val item = pagerAdapter?.getItem(position) ?: return@TabLayoutMediator
                tab.icon = ContextCompat.getDrawable(requireContext(), item.iconRes)
                tab.text = item.type.name
            }.attach()
        }
        pagerAdapter?.setList(viewModel.getPagerItems())
    }

    private fun navigateToSearch() {

    }

    private fun navigateToAuth() {
//        findNavController().navigate(CollectionFragmentDirections.actionMenuFragmentToAuthParentFragment())
        findNavController().navigate(CollectionFragmentDirections.actionMenuFragmentToAuthGraph())
    }

    private fun navigateToEditor() {
        findNavController().navigate(CollectionFragmentDirections.actionMenuFragmentToEditorFragment())
    }
}