package com.example.todoapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

const val TAG = "FirstActivity"


class MainActivity : AppCompatActivity() {
    val toDoContents = arrayListOf<String>()
    val REQUEST_CODE = 414
    private val FILE_NAME = "TaskList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
    }

    override fun onStart () {
        super.onStart()
        val toDoContentsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoContents)
        toDoListView.adapter = toDoContentsAdapter
        toDoListView.setOnItemLongClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Removing $selectedItem", Toast.LENGTH_SHORT).show()
            toDoContents.removeAt(position)
            toDoContentsAdapter.notifyDataSetChanged()
            if (toDoContents.isEmpty()) {
                Toast.makeText(this, "All tasks are completed!", Toast.LENGTH_SHORT).show()
            }
            return@setOnItemLongClickListener true
        }
    }

    fun addToDoOnClick (view: View) {
        val myIntent = Intent(this, SecondActivity::class.java)
        startActivityForResult(myIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
                // extract the data from the intent
            val task = data?.getStringArrayListExtra("todo")
            if (task != null){
                Log.d(TAG, "onActivityResult: $task")
                for (addedTask in task) {
                    toDoContents.add(addedTask)
                }
                saveData()
            }
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val taskListJson = gson.toJson(toDoContents)
        editor.putString("todo", taskListJson)
        editor.apply()
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val tasks = sharedPreferences.getString("todo", "") ?: ""
        if (tasks.isNotEmpty()) {
            val gson = Gson()
            val sType = object : TypeToken<List<String>>() {}.type
            val loadedTask = gson.fromJson<List<String>>(tasks, sType)
            for (task in loadedTask) {
                toDoContents.add(task)
            }
        }
    }
}