package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toDoContents = listOf<String>()
        val toDoContentsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoContents)
        toDoListView.adapter = toDoContentsAdapter

    }

    fun addToDoOnClick (view: View) {
        val myIntent = Intent(this, MainActivity2::class.java)
        startActivity(myIntent)
    }


}