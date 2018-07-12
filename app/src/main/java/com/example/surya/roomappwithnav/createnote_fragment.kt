package com.example.surya.roomappwithnav


import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import com.example.surya.roomappwithnav.DatabaseFile.RoomDatabases
import com.example.surya.roomappwithnav.Modal.Note
import com.example.surya.roomappwithnav.Viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_createnote_fragment.*
import java.sql.Timestamp


class createnote_fragment : Fragment() {

    var flagForCreateEdit: Int = 0
    lateinit var editKey: String
    lateinit var editNote: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_createnote_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = getArguments()
        flagForCreateEdit = args!!.getInt("Edit", 0)

        if (flagForCreateEdit == 1) {
            editNote = args.getString("Note")
            editKey = args.getString("key")
            note.setText(editNote)
            setHasOptionsMenu(true)
        }

        note.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if(p0?.length!! > 0) {
                    setHasOptionsMenu(true)
                } else {
                    setHasOptionsMenu(false)
                }
            }
        })

    }

    fun addNotes() {
        val Db = Room.databaseBuilder(this.requireContext(),RoomDatabases::class.java,"note.db").allowMainThreadQueries().build()
        val noteObj = Note()
        noteObj.note = note.text.toString()
        if (flagForCreateEdit == 1) {
            noteObj.id = editKey.toLong()
            Db.noteDao().update(noteObj)

        } else {
            val Timestampkey:Long = Timestamp(System.currentTimeMillis()).time
            noteObj.id = Timestampkey
            Db.noteDao().insert(noteObj)
        }
        note.setText("")
        (activity as MainActivity).onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.save -> {
                addNotes()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        menuInflater?.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu,menuInflater);
    }

}

