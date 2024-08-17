package com.example.ageinminutes

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var selectedDate : TextView ? = null
    private var ageInMinutes : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)

        selectedDate = findViewById(R.id.SelectedDate)
        ageInMinutes = findViewById(R.id.ageInMinutes)

        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }
    }

    fun clickDatePicker(){
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            {
                    _, year, month, dayOfMonth ->
                Toast.makeText(this,"Year was $year,month was ${month + 1} and day was $dayOfMonth" , Toast.LENGTH_SHORT).show()

                val selecteddate = "$dayOfMonth/${month + 1 }/$year"

                selectedDate?.setText(selecteddate)


                val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selecteddate)
                theDate?.let{
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    currentDate?.let{
                        val currentDateInMinutes = currentDate.time/60000
                        val diffInMinutes = currentDateInMinutes - selectedDateInMinutes
                        ageInMinutes?.text = diffInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}