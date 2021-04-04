package com.makaroni.chocho.data.db

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.makaroni.chocho.data.db.TrainImage.Companion.COLUMN_ID
import com.makaroni.chocho.data.db.TrainImage.Companion.TABLE_NAME


@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = TrainModel::class,
        parentColumns = arrayOf(COLUMN_ID),
        childColumns = arrayOf(COLUMN_ID),
        onDelete = CASCADE
    )]
)
data class TrainImage(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int,
    @ColumnInfo(name = COLUMN_IMAGE, typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray
) {
    companion object {
        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_IMAGE = "image"
        const val TABLE_NAME = "image"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TrainImage

        if (id != other.id) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + image.contentHashCode()
        return result
    }
}
