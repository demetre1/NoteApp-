package com.example.noteapp

import android.accounts.AccountManager.get
import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class NoteEditorActivity : AppCompatActivity() {
    lateinit var noteEditText:EditText

    private var  noteId :Int? = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)
        var sharedpreferences = this.getSharedPreferences("com.example.noteapp",Context.MODE_PRIVATE)
         noteEditText = findViewById(R.id.note_EditText)



        noteId = intent.getIntExtra("noteId",-1);


        if (noteId != -1) {
            noteEditText.setText(MainActivity.notes[noteId])
        } else {
            MainActivity.notes.add("")
            noteId = MainActivity.notes.size() - 1
            MainActivity.arrayAdapter.notifyDataSetChanged()
        }



    }


}





