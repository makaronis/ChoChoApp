package com.makaroni.chocho.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makaroni.chocho.MainActivity
import com.makaroni.chocho.MainActivityKt
import com.makaroni.chocho.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentMenuBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mainActivity = requireActivity() as MainActivityKt
        mainActivity.subscribeToolbarLeadingButton(this, fragmentBinding.toolbar)
    }
}