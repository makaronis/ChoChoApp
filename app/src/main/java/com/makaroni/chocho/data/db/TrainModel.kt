package com.makaroni.chocho.data.db

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makaroni.chocho.data.db.TrainModel.Companion.TABLE_NAME
import com.makaroni.chocho.data.model.CollectionType

@Entity(tableName = TABLE_NAME)
data class TrainModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = COLUMN_TYPE)
    val type: CollectionType,
    @ColumnInfo(name = COLUMN_SUBTYPE)
    val subtype: String,
    @ColumnInfo(name = COLUMN_ADDITIONAL_TYPE)
    val additionalType: String,
    @ColumnInfo(name = COLUMN_ARTICLE)
    val article: Int? = null,
    @ColumnInfo(name = COLUMN_MANUFACTURER)
    val manufacturer: String? = null,
    @ColumnInfo(name = COLUMN_MODEL)
    val modelName: String? = null,
    @ColumnInfo(name = COLUMN_RAILWAY_COMPANY)
    val railwayCompany: String? = null,
    @ColumnInfo(name = COLUMN_IMAGE)
    val image: String? = null,
    @ColumnInfo(name = COLUMN_NOTE)
    val note: String? = null

) {
    companion object {
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_ARTICLE = "article"
        const val COLUMN_MANUFACTURER = "manufacturer"
        const val COLUMN_MODEL = "model"
        const val COLUMN_RAILWAY_COMPANY = "company"
        const val COLUMN_NOTE = "note"
        const val COLUMN_TYPE = "type"
        const val COLUMN_SUBTYPE = "subtype"
        const val COLUMN_ADDITIONAL_TYPE = "additionalType"
        const val COLUMN_IMAGE = "image"
        const val TABLE_NAME = "trains"
    }
}
