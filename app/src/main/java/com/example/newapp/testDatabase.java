package com.example.newapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1,exportSchema = false,entities = {User.class})
public abstract class testDatabase extends RoomDatabase {

    public static testDatabase getInstance(Context context){
        return Room.databaseBuilder(context,testDatabase.class,"testDB").allowMainThreadQueries().build();
    }
    public abstract UserDao userDao();
}
