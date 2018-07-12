package com.example.surya.roomappwithnav.DaoFile

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.example.surya.roomappwithnav.Modal.Note


@Dao
interface NoteDao {
//
//    @Query("SELECT * from notetable ORDER BY id DESC")
//    fun getAll(): LiveData<MutableList<Note>>

    @Insert
    fun insert(user: Note)

    @Update
    fun update(user: Note)

    @Delete
    fun delete(user: Note)

    @Query("DELETE from notetable")
    fun deleteAll()

    @Query("SELECT * from notetable ORDER BY id DESC")
    abstract fun getAllNotes(): DataSource.Factory<Int, Note>

}