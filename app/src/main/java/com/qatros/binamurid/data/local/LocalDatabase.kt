package com.qatros.binamurid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qatros.binamurid.data.local.dao.ExampleDao
import com.qatros.binamurid.data.local.entity.ExampleEntity

@Database(
    entities = [
        ExampleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
}