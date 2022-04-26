package com.makaroni.chocho.legacy.ui.collection

import androidx.lifecycle.ViewModel
import com.makaroni.chocho.R
import com.makaroni.chocho.data.model.CollectionItem
import com.makaroni.chocho.data.model.CollectionType

class CollectionViewModel : ViewModel() {

    fun getPagerItems(): List<CollectionItem> {
        return listOf(
                CollectionItem(CollectionType.ALL, R.drawable.ic_railway),
                CollectionItem(CollectionType.LOCOMOTIVES, R.drawable.ic_train),
                CollectionItem(CollectionType.WAGONS, R.drawable.ic_wagon)
        )
    }
}