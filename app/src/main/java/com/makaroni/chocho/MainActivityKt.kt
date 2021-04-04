package com.makaroni.chocho

import android.content.ContentValues
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.makaroni.chocho.data.TrainsContract
import com.makaroni.chocho.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivityKt : FragmentActivity() {

    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        putDummy()
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

    fun putDummy(){
        //Put data into content values
        val values = ContentValues()
        values.put(TrainsContract.TrainsEntry.COLUMN_ARTICLE, Random.nextInt(0,1000))
        values.put(TrainsContract.TrainsEntry.COLUMN_MANUFACTURER, "das")
        values.put(TrainsContract.TrainsEntry.COLUMN_MODEL, "model")
        values.put(TrainsContract.TrainsEntry.COLUMN_COMPANY, "company")
        values.put(TrainsContract.TrainsEntry.COLUMN_NOTE, "note")
        values.put(TrainsContract.TrainsEntry.COLUMN_TYPE, "type")
        values.put(TrainsContract.TrainsEntry.COLUMN_SUBTYPE, "subtype")
        contentResolver.insert(TrainsContract.TRAINS_CONTENT_URI, values)
    }

}