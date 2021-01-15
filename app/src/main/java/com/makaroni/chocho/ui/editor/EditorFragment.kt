package com.makaroni.chocho.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makaroni.chocho.MainActivityKt
import com.makaroni.chocho.databinding.FragmentEditorBinding

class EditorFragment : Fragment() {

    private lateinit var fragmentBinding: FragmentEditorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentBinding = FragmentEditorBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToolbar()
    }

    private fun subscribeToolbar(){
        val activity = requireActivity() as MainActivityKt
        activity.subscribeToolbarLeadingButton(this,fragmentBinding.toolbar)
    }

}