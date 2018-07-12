package com.example.surya.roomappwithnav.Modal

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.v7.util.DiffUtil

@Entity(tableName = "notetable")
data class Note(@PrimaryKey(autoGenerate = true) var id: Long?,@ColumnInfo(name = "note") var note: String)
{
    constructor() : this(null, "")

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
}