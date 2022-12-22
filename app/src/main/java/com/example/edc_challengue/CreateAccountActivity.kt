package com.example.edc_challengue

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edc_challengue.database.UserDatabase
import com.example.edc_challengue.database.UserEntity
import com.example.edc_challengue.databinding.ActivityCreateAccountBinding
import java.text.SimpleDateFormat
import java.util.*

class CreateAccountActivity : AppCompatActivity(), AdapterView.OnItemClickListener{

    private lateinit var mBinding: ActivityCreateAccountBinding
    lateinit var birthdate: String

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        birthdate = null.toString()

        supportActionBar?.hide()

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

        mBinding.btnSave.setOnClickListener {
            with(mBinding){
                var name = tieLastname.text.isNullOrEmpty()
                var lastname = tieLastname.text.isNullOrEmpty()

                var gender = autoCompleteTextView.text
                var pass1 = password1.text
                var pass2 = password2.text

                var email = tieEmail.text.isNullOrEmpty()
                var notifications = cbNotifications.isChecked

                var c = mBinding.tvDate.text

                if(!name && !lastname && c != "Birthdate" && gender.toString() != "" && pass1.toString() == pass2.toString() && !email){

                    val userEntity = UserEntity()

                    userEntity.name = mBinding.tieName.text.toString()
                    userEntity.lastname = mBinding.tieLastname.text.toString()
                    userEntity.birthdate = birthdate
                    userEntity.email = mBinding.tieEmail.text.toString()
                    userEntity.gender = mBinding.autoCompleteTextView.toString()

                    userEntity.password = pass1.toString()
                    userEntity.notifications = notifications

                    val userDatabase  = UserDatabase.getUserDatabase(applicationContext)
                    val userDao = userDatabase?.userDao()

                    Thread{
                        userDao?.registerUser(userEntity)
                        runOnUiThread {
                            Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()
                        }
                        startActivity(Intent(this@CreateAccountActivity, MainActivity::class.java))
                    }.start()


                }else{
                    Toast.makeText(baseContext, "Llena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            }

    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf =  SimpleDateFormat(myFormat, Locale.UK)
        birthdate = sdf.format(myCalendar.time)
        mBinding.tvDate.text = "Birthdate \n ${sdf.format(myCalendar.time)}"
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = p0?.getItemAtPosition(p2).toString()
        setDataInView(item)
    }

    private fun setDataInView(item: String) {

    }
}