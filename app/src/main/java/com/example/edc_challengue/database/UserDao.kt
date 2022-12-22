package com.example.edc_challengue.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun registerUser(userEntity: UserEntity?)

    @Query("SELECT * FROM usuarios where email=(:email) and password=(:password)")
    fun login(email: String?, password: String?): UserEntity?

    @Query("SELECT * FROM usuarios where email=(:email) and password=(:password)")
    fun getData(email: String?, password: String?): UserEntity?


}