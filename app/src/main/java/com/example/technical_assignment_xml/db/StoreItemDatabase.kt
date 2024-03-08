package com.example.technical_assignment_xml.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.technical_assignment_xml.domain.models.StoreItem
import com.example.technical_assignment_xml.db.StoreItemDao


@Database(
    entities = [StoreItem::class],
    version = 1,
    exportSchema = false
)
abstract class StoreItemDatabase : RoomDatabase() {

    abstract val storeItemDao: StoreItemDao
}