package com.example.edc_challengue

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.edc_challengue.databinding.ActivityCreateAccountBinding
import java.text.SimpleDateFormat
import java.util.*

class CreateAccountActivity : AppCompatActivity(), AdapterView.OnItemClickListener{

    private lateinit var mBinding: ActivityCreateAccountBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        mBinding.btnDate.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        val gender = resources.getStringArray(R.array.genders)
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item,
            gender
        )
        with(mBinding.autoCompleteTextView){
            setAdapter(adapter)
            onItemClickListener = this@CreateAccountActivity
        }

    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf =  SimpleDateFormat(myFormat, Locale.UK)
        mBinding.tvDate.text = "Birthdate \n ${sdf.format(myCalendar.time)}"
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = p0?.getItemAtPosition(p2).toString()
        setDataInView(item)
    }

    private fun setDataInView(item: String) {

    }
}