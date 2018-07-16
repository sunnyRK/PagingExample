package com.example.surya.roomappwithnav.Adapters

import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.arch.persistence.room.Room
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.surya.roomappwithnav.DaoFile.NoteDao
import com.example.surya.roomappwithnav.DatabaseFile.RoomDatabases
import com.example.surya.roomappwithnav.MainActivity
import com.example.surya.roomappwithnav.Modal.Note

import com.example.surya.roomappwithnav.R
import com.example.surya.roomappwithnav.R.id.note
import com.example.surya.roomappwithnav.R.id.notedesc
import com.example.surya.roomappwithnav.Viewmodel.MainViewModel
import com.example.surya.roomappwithnav.listfragment
import kotlinx.android.synthetic.main.fragment_createnote_fragment.*
import kotlinx.android.synthetic.main.note_card.view.*

class NoteAdap(view: View,viewModel: MainViewModel,listFragmentObj: listfragment):
        PagedListAdapter<Note,NoteViewHolder>(DiffCallback)
{
    var view: View
    var viewModel: MainViewModel
    var noteDao: NoteDao
    var listFragmentObj:listfragment
    var Db: RoomDatabases? = null
    init {
        this.view = view
        this.viewModel = viewModel
        Db = Room.databaseBuilder(view.context,RoomDatabases::class.java,"note.db").allowMainThreadQueries().build()
        noteDao = Db!!.noteDao()
        this.listFragmentObj = listFragmentObj
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteObj = getItem(position)
        if (noteObj != null) {
            holder.bindTo(noteObj)
        }

        holder.notedescription.setOnClickListener {
            var mArgs =Bundle()
            mArgs.putInt("Edit", 1)
            mArgs.putString("Note", noteObj?.note)
            mArgs.putString("key", noteObj?.id.toString())
            Navigation.findNavController(view).navigate(R.id.createnote_fragment,mArgs)
        }

        holder.notedeleteBtn.setOnClickListener {
            val Db = Room.databaseBuilder(view.context, RoomDatabases::class.java,"note.db").allowMainThreadQueries().build()
            Db.noteDao().delete(noteObj!!)
            viewModel.initMethod(noteDao)
            listFragmentObj.ViewModelCalling(view)
            notifyDataSetChanged()
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note?, newItem: Note?): Boolean
                    = oldItem != null && newItem != null && oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note?, newItem: Note?): Boolean
                    = oldItem != null && newItem != null
                    && oldItem.id == newItem.id
                    && oldItem.note == newItem.note
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder{
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false)
        return NoteViewHolder(v)
    }
}