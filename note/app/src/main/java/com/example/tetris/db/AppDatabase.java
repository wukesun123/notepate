package com.example.tetris.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tetris.dao.DiaryDao;
import com.example.tetris.dao.ListcardDao;
import com.example.tetris.dao.UserDao;
import com.example.tetris.entity.Diary;
import com.example.tetris.entity.Listcard;
import com.example.tetris.entity.User;

@Database(entities = { User.class , Diary.class, Listcard.class}, version = 13)//
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract DiaryDao diaryDao();
    public abstract ListcardDao listcardDao();
}