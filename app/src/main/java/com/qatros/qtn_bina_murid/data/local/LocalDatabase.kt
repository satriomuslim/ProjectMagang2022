package com.qatros.qtn_bina_murid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qatros.qtn_bina_murid.data.local.dao.ExampleDao
import com.qatros.qtn_bina_murid.data.local.entity.ExampleEntity

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