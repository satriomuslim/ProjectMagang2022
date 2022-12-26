package com.qatros.binamurid.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "class_db")
class ExampleEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name= "id")
    val id: Int?
) : Parcelable