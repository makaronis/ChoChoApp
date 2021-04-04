package com.makaroni.chocho.data.db

import androidx.room.TypeConverter
import com.makaroni.chocho.data.model.CollectionType

class TypeConverter {

    @TypeConverter
    fun toCollectionType(type: String) = CollectionType.valueOf(type)

    @TypeConverter
    fun fromCollectionType(type: CollectionType) = type.name
}