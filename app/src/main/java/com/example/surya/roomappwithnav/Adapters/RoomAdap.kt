package com.example.surya.roomappwithnav.Adapters

import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.arch.persistence.room.Room
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.surya.roomappwithnav.DatabaseFile.RoomDatabases
import com.example.surya.roomappwithnav.MainActivity
import com.example.surya.roomappwithnav.Modal.Note
import com.example.surya.roomappwithnav.R
import com.example.surya.roomappwithnav.R.id.note
import com.example.surya.roomappwithnav.R.id.notedesc
import com.example.surya.roomappwithnav.listfragment
import kotlinx.android.synthetic.main.fragment_createnote_fragment.*
import kotlinx.android.synthetic.main.note_card.view.*

class RoomAdap(context: Context, private var items: PagedList<Note>,view: View): PagedListAdapter<Note,RoomAdap.ViewHolder>(Note.DiffCallback)
{
    var context: Context
    var view: View

    init {
        this.context = context
        this.view = view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val notedesc: String? = items.get(position)?.note
        val id: String = items.get(position)?.id.toString()
        holder.notedescription?.text = notedesc

        holder.notedescription.setOnClickListener {
            var mArgs =Bundle()
            mArgs.putInt("Edit", 1)
            mArgs.putString("Note", notedesc)
            mArgs.putString("key", items.get(position)?.id.toString())
            Navigation.findNavController(view).navigate(R.id.createnote_fragment,mArgs)
        }

        holder.notedeleteBtn.setOnClickListener {
            val Db = Room.databaseBuilder(context, RoomDatabases::class.java,"note.db").allowMainThreadQueries().build()
            val noteObj = Note()
            noteObj.note = notedesc.toString()
            noteObj.id = id.toLong()
            Db.noteDao().delete(noteObj)
            items.removeAt(position)
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        var notedescription = view.notedesc
        var notedeleteBtn = view.delNote
    }
}