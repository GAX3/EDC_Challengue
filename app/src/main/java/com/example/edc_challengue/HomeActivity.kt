package com.example.edc_challengue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.edc_challengue.database.UserDatabase
import com.example.edc_challengue.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val userDatabase = UserDatabase.getUserDatabase(applicationContext)
        val userDao = userDatabase?.userDao()

        var name = intent.getStringExtra("name")
        var password = intent.getStringExtra("password")

        mBinding.tvWelcome.text = "${name.toString()} + ${password.toString()}"

        Thread{
            val userEntity = userDao?.getData(name, password)
            if(userEntity != null){
                val name = userEntity.name
                val birthdate = userEntity.birthdate

                mBinding.tvTest.text = "$name + $birthdate"

            }

        }.start()


    }
}