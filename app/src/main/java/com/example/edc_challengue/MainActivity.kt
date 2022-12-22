package com.example.edc_challengue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.edc_challengue.database.UserDatabase
import com.example.edc_challengue.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mBinding.newAccount.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }

        mBinding.image.setOnClickListener {
            Toast.makeText(baseContext, "Click", Toast.LENGTH_SHORT).show()
        }
        supportActionBar?.hide()

        mBinding.btnLogin.setOnClickListener {
            val userIdText = mBinding.tieName.text.toString()
            val passwordText = mBinding.tiePassword.text.toString()


            if(!mBinding.tieName.text.isNullOrEmpty() && !mBinding.tiePassword.text.isNullOrEmpty()){
                val userDatabase = UserDatabase.getUserDatabase(applicationContext)
                val userDao = userDatabase?.userDao()

                Thread{
                    val userEntity = userDao?.login(userIdText, passwordText)
                    if(userEntity == null){
                        runOnUiThread{
                            Toast.makeText(applicationContext, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        val name = userEntity.email
                        startActivity(
                            Intent(this@MainActivity, HomeActivity::class.java).putExtra(
                                "name", name
                            ).putExtra("password", passwordText)
                        )
                    }
                }.start()
            }
        }
    }
}