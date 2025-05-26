package com.example.newapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(version = 1, exportSchema = false, entities = {User.class})
public abstract class testDatabase extends RoomDatabase {

    private static testDatabase instance;

    public abstract UserDao userDao();

    public static synchronized testDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            testDatabase.class, "testDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries() // فقط برای تست، در اپ نهایی حذف کن
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // اینجا می‌تونی دیتاهای اولیه وارد کنی، مثلاً:
            // Executors.newSingleThreadExecutor().execute(() -> instance.userDao().insertUser(new User("Ali", "Rezaei")));
        }
    };
}