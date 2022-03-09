package com.example.todoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.ArrayList

class MainActivity2 : AppCompatActivity() {
    var count = 0
    val toDoContents = listOf<String>().toMutableList()
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    fun saveAddNewOnClick(view: View) {
        val task = addTaskEditText.text.toString()
        if (task.isEmpty()) {
            Toast.makeText(this, "Please add a task", Toast.LENGTH_SHORT).show()
            return
        }
/*        val secondIntent = Intent(this, MainActivity:: class.java)
        secondIntent.putExtra("task", task)*/


        toDoContents.add(task)
        val jsonString = gson.toJson(toDoContents)

        val sType = object : TypeToken<List<String>>() { }.type
        val myList = gson.fromJson<List<String>>(jsonString, sType)

        Log.d(TAG, "$myList")

        val myIntent = Intent()
        myIntent.putStringArrayListExtra("task", myList as ArrayList<String>?)
        setResult(Activity.RESULT_OK, myIntent)
        addTaskEditText.text.clear()
    }

    fun saveGoBackOnClick(view: View) {
        val secondIntent = Intent(this, MainActivity::class.java)
        startActivity(secondIntent)
    }

    fun goBackWithoutSaveOnClick(view: View) {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }

}