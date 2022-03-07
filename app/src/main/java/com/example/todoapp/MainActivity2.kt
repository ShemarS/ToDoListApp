package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    var count = 0
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
        

    }

    fun saveGoBackOnClick(view: View) {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }

    fun goBackWithoutSaveOnClick(view: View) {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }

}