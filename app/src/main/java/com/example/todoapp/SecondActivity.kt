package com.example.todoapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_second.*
import java.io.Serializable
import java.util.ArrayList

class SecondActivity : AppCompatActivity() {
    var count = 0
    val toDoContents = ArrayList<String>()
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
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
        addTaskEditText.text.clear()
        addTaskEditText.hideKeyboard()

    }

    fun saveGoBackOnClick(view: View) {
/*        val secondIntent = Intent(this, MainActivity::class.java)
        startActivity(secondIntent)*/

       /* val jsonString = gson.toJson(toDoContents)
        val sType = object : TypeToken<List<String>>() { }.type
        val myList = gson.fromJson<List<String>>(jsonString, sType)

        Log.d(TAG, "$myList")*/

        val myIntent = Intent()
        myIntent.putStringArrayListExtra("todo", toDoContents)
        setResult(Activity.RESULT_OK, myIntent)
        finish()
    }

    fun goBackWithoutSaveOnClick(view: View) {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }

    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

}