package com.example.tetris.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tetris.entity.Diary;
import com.example.tetris.entity.Listcard;
import com.example.tetris.entity.User;

import java.util.List;
@Dao
public interface ListcardDao {
    @Query("SELECT * FROM listcard WHERE uid = :uid")
    List<Listcard> findListcards( int uid); // 条件查询
    @Query("SELECT * FROM listcard WHERE time = :time")
    Listcard findListcard(String time); // 条件查询
    @Insert
    void insert(Listcard list); // 新增，由于主键自增，user不用设置uid值
    @Delete
    void delete(Listcard list); // 单个删除
    @Update
    void update(Listcard list); // 单个更新
}