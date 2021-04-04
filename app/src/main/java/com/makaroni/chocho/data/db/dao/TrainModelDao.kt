package com.makaroni.chocho.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makaroni.chocho.data.db.TrainImage
import com.makaroni.chocho.data.db.TrainModel

@Dao
interface TrainModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(train: TrainModel)

    @Query("DELETE FROM ${TrainModel.TABLE_NAME} WHERE ${TrainModel.COLUMN_ID} = :id")
    fun deleteById(id: Int)

    @Query("DELETE FROM ${TrainModel.TABLE_NAME}")
    fun deleteAll()

    @Query("SELECT * FROM ${TrainModel.TABLE_NAME}")
    fun selectAllLive(): LiveData<List<TrainImage>>

    @Query("SELECT * FROM ${TrainModel.TABLE_NAME}")
    fun selectAll(): List<TrainImage>
}