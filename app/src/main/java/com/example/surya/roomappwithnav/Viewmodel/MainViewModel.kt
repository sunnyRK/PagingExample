package com.example.surya.roomappwithnav.Viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.example.surya.roomappwithnav.DatabaseFile.RoomDatabases
import com.example.surya.roomappwithnav.Modal.Note

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val Db: RoomDatabases
    var list: LiveData<PagedList<Note>>
    init {

        Db = Room.databaseBuilder(application, RoomDatabases::class.java, "note.db").allowMainThreadQueries().build()

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(11)
                .setPageSize(11)
                .build()

        list = LivePagedListBuilder<Int, Note>(Db.noteDao().getAllNotes(), pagedListConfig).build()

    }

}

