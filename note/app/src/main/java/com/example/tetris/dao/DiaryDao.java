package com.example.tetris.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tetris.entity.Diary;
import com.example.tetris.entity.User;

import java.util.List;

@Dao
public interface DiaryDao {
    @Query("SELECT * FROM diary WHERE uid = :uid")
    List<Diary> findDiarys(int uid); // 条件查询
    @Query("SELECT * FROM diary WHERE time = :time")
    Diary findDiary(String time); // 条件查询
    @Insert
    void insert(Diary diary); // 新增，
    @Delete
    void delete(Diary diary); // 单个删除
    @Update
    void update(Diary diary); // 单个更新
}
