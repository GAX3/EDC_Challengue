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



        Thread{
            val userEntity = userDao?.getData(name, password)
            if(userEntity != null){
                val name = userEntity.name
                val lastname = userEntity.lastname
                val birthdate = userEntity.birthdate
                val email = userEntity.email
                val gender = userEntity.gender
                val notification = userEntity.notifications

                mBinding.name1.text = name
                mBinding.lastname1.text = lastname
                mBinding.birthdate1.text = birthdate
                mBinding.email1.text = email
                mBinding.gender1.text = gender

                if(notification == true){|
                    mBinding.noti1.text = "Activated"
                }else{
                    mBinding.noti1.text = "Inactivaded"
                }


            }

        }.start()


    }
}