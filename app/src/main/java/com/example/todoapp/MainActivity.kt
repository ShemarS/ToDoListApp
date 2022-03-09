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

    fun saveData(){

        // Create an instance of getSharedPreferences for edit
       val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Create an instance of Gson (make sure to include its dependency first to be able use gson)
        val gson = Gson()
        // toJson() method serializes the specified object into its equivalent Json representation.
        val taskListJson = gson.toJson(toDoContents)
        // Put the  Json representation, which is a string, into sharedPreferences
        editor.putString("todo", taskListJson)
        // Apply the changes
        editor.apply()

        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
    }

    fun loadData() {

        // Create an instance of getSharedPreferences for retrieve the data
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        // Retrieve data using the key, default value is empty string in case no saved data in there
        val tasks = sharedPreferences.getString("todo", "") ?: ""

        if (tasks.isNotEmpty()) {

            // Create an instance of Gson
            val gson = Gson()
            // create an object expression that descends from TypeToken
            // and then get the Java Type from that
            val sType = object : TypeToken<List<String>>() {}.type
            // provide the type specified above to fromJson() method
            // this will deserialize the previously saved Json into an object of the specified type (e.g., list)
            val loadedTask = gson.fromJson<List<String>>(tasks, sType)
            for (task in loadedTask) {
                toDoContents.add(task)
            }

        }
    }
}