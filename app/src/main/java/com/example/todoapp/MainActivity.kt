package com.example.todoapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "FirstActivity"


class MainActivity : AppCompatActivity() {
    val toDoContents = listOf<String>().toMutableList()
    val REQUEST_CODE = 414

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun OnStart () {
        super.onStart()
        val toDoContentsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoContents)
        toDoListView.adapter = toDoContentsAdapter

        toDoListView.setOnItemLongClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Removing $selectedItem", Toast.LENGTH_SHORT).show()
            toDoContents.removeAt(position)
            toDoContentsAdapter.notifyDataSetChanged()
            return@setOnItemLongClickListener true
        }
    }

    fun addToDoOnClick (view: View) {
        val myIntent = Intent(this, MainActivity2::class.java)
        startActivityForResult(myIntent, REQUEST_CODE)
    }

    /*
        override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume was called")
    }
    */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
                // extract the data from the intent
            val task = data?.getStringExtra("task")
            if (task != null) {
                toDoContents.add(task)
            }
        }
    }


}