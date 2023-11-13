package com.example.tetris.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary",primaryKeys = {"uid","time"})
public class Diary {
    @NonNull
    public int uid;
    @ColumnInfo(name = "title")
    public String title;
    @NonNull
    @ColumnInfo(name = "time")
    public String time;
    @ColumnInfo(name = "content")
    public String content;

}

