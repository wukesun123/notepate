package com.example.tetris.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tetris.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll(); // 查询所有
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User findUser( String username, String password ); // 条件查询
    @Query("SELECT * FROM user WHERE username = :username")
    User findUserin( String username);
    @Insert
    void insert(User user); // 新增，由于主键自增，user不用设置uid值
    @Delete
    void delete(User user); // 单个删除
    @Update
    void update(User user); // 单个更新
    @Query("DELETE FROM user")
    void deleteAll(); // 删除所有（批量删除）
}

