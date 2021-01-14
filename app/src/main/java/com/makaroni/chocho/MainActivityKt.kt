package com.makaroni.chocho

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.makaroni.chocho.databinding.ActivityMainBinding

class MainActivityKt : FragmentActivity() {

    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
    }

    fun subscribeToolbarLeadingButton(
            fragment: Fragment,
            toolbar: Toolbar
    ) {
        if (fragment.parentFragmentManager.backStackEntryCount == 0) {
            toolbar.setNavigationIcon(R.drawable.ic_toolbar_menu)
            toolbar.setNavigationOnClickListener { toggleDrawer() }
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    fun toggleDrawer() {
        if (activityBinding.drawer.isDrawerOpen(GravityCompat.START)) {
            activityBinding.drawer.closeDrawer(GravityCompat.START)
        } else {
            activityBinding.drawer.openDrawer(GravityCompat.START)
        }
    }

}