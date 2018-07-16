package com.example.surya.roomappwithnav


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.surya.roomappwithnav.Adapters.NoteAdap
//import com.example.surya.roomappwithnav.Adapters.RoomAdap
import com.example.surya.roomappwithnav.DaoFile.NoteDao
import com.example.surya.roomappwithnav.DatabaseFile.RoomDatabases
import com.example.surya.roomappwithnav.Modal.Note
import com.example.surya.roomappwithnav.Viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_listfragment.*

class listfragment : Fragment() {
    var Db: RoomDatabases? = null
    lateinit var adapNote: NoteAdap
    lateinit var noteDao: NoteDao

    val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_listfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Db = Room.databaseBuilder(this.requireContext(),RoomDatabases::class.java,"note.db").allowMainThreadQueries().build()
        noteDao = Db!!.noteDao()

        viewModel.initMethod(noteDao)
        ViewModelCalling(view)

        addNote.setOnClickListener {
            var mArgs =Bundle()
            mArgs.putInt("Edit", 0)
            val options = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .build()
            Navigation.findNavController(view).navigate(R.id.createnote_fragment,mArgs,options)
        }
    }

    fun ViewModelCalling(view: View)
    {
        viewModel.list.observe(this, object : Observer<PagedList<Note>> {
            override fun onChanged(notelist: PagedList<Note>?) {
                if (notelist != null) {
                    Toast.makeText(context,"hey1",Toast.LENGTH_SHORT).show()
                    adapNote = NoteAdap(view,viewModel,this@listfragment)
                    adapNote.submitList(notelist)
                    rvRoom?.layoutManager = LinearLayoutManager(context)
                    rvRoom?.itemAnimator = DefaultItemAnimator()
                    rvRoom?.adapter = adapNote
                }
            }
        })
    }
}
