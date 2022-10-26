package com.example.noteapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var itemToDelate:Int = 0
    lateinit var notesListView: ListView
    lateinit var emptyTv:TextView
    lateinit var notes : ArrayList<String>;
    lateinit var adapter: ArrayAdapter<Any?>
    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        sharedPreferences = this.getSharedPreferences("com.example.noteapp",Context.MODE_PRIVATE);
        notesListView = findViewById(R.id.notes_ListView)
        emptyTv = findViewById(R.id.emptyTV)
        notes =  ArrayList<String>()

        var noteSet:HashSet<String> = sharedPreferences.getStringSet("notes", null) as HashSet<String>
        val set = sharedPreferences.getStringSet("notes", null) as java.util.HashSet<String?>?


        if(noteSet.isEmpty() || noteSet == null){
            emptyTv.visibility = View.VISIBLE


        }else{
            emptyTv.visibility = View.GONE
            notes =  ArrayList(noteSet);
        }
        adapter = ArrayAdapter(applicationContext,R.layout.custome_notes_row,R.id.notesTV, notes as List<Any?>
        )
        notesListView.setAdapter(adapter)

        notesListView.setOnItemClickListener{parent: AdapterView<*>?,
        view: View?, position: Int, l: Long ->
            intent = Intent(applicationContext,NoteEditorActivity::class.java);
            intent.putExtra("noteId",position);
            startActivity(intent)
        }
        notesListView.setOnItemClickListener {  parent: AdapterView<*>?,
                                                view: View?, position: Int, l: Long ->
            itemToDelate = position
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Are You Sure")
                .setMessage("Do you Want to Delate this note?")
                .setPositiveButton("Yes",DialogInterface.OnClickListener{
                    dialog:DialogInterface, which: Int ->
                    notes!!.slice(itemToDelate.rangeTo(-1))
                    adapter.notifyDataSetChanged()
                    noteSet= HashSet<String>(notes)
                    sharedPreferences.edit().putStringSet("notes",noteSet).apply()
                    if(notes!!.isEmpty() || noteSet == null){
                        emptyTv.visibility = View.VISIBLE
                    }

                }).setNegativeButton("No",null)
                .show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

           menuInflater.inflate(R.menu.add_note_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         super.onOptionsItemSelected(item)
        if(item.itemId == R.id.add_note){
            startActivity(Intent(applicationContext,NoteEditorActivity::class.java));
            return true
        }
        return false
    }


}



