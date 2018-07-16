package com.example.surya.roomappwithnav.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.surya.roomappwithnav.Modal.Note
import kotlinx.android.synthetic.main.note_card.view.*

class NoteViewHolder(view: View): RecyclerView.ViewHolder(view)
{
    var notedescription = view.notedesc
    var notedeleteBtn = view.delNote
    lateinit var note: Note

    fun bindTo(note: Note) {
        this.note = note
        notedescription?.text = note.note
    }
}