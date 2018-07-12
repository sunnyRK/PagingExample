package com.example.surya.roomappwithnav.Viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.example.surya.roomappwithnav.DaoFile.NoteDao
import com.example.surya.roomappwithnav.DatabaseFile.RoomDatabases
import com.example.surya.roomappwithnav.Modal.Note

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val Db: RoomDatabases
    lateinit var list: LiveData<PagedList<Note>>
    init {

        Db = Room.databaseBuilder(application, RoomDatabases::class.java, "note.db").allowMainThreadQueries().build()
//        val pagedListConfig = PagedList.Config.Builder()
//                .setEnablePlaceholders(true)
//                .setPrefetchDistance(10)
//                .setPageSize(10)
//                .build()
//
//        list = LivePagedListBuilder<Int,Note>(Db.noteDao().getAllNotes(), pagedListConfig).build()

    }

    fun initMethod(noteDao: NoteDao)
    {
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(5)
                .setPageSize(20)
                .build()

        list = LivePagedListBuilder(noteDao.getAllNotes(), pagedListConfig).build()
    }
}

