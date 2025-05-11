package com.example.newapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User... user);

    @Query("SELECT * FROM User")
    List<User> getUsers();

}
