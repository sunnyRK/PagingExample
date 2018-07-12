package com.example.surya.roomappwithnav.DatabaseFile

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.surya.roomappwithnav.DaoFile.NoteDao
import com.example.surya.roomappwithnav.Modal.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class RoomDatabases : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}