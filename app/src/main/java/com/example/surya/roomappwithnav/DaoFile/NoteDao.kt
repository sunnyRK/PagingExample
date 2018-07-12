package com.example.surya.roomappwithnav.DaoFile

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.example.surya.roomappwithnav.Modal.Note


@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE from notetable")
    fun deleteAll()

    @Query("SELECT * from notetable ORDER BY id DESC")
    abstract fun getAllNotes(): DataSource.Factory<Int, Note>

}