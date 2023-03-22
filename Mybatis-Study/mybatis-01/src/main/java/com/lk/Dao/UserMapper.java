package com.lk.Dao;

import com.lk.pojo.User;

import java.util.List;

public interface UserMapper {
    //查询全部用户
    List<User> getUserList();

//    根据id查询用户
    User getUserById(int id);

    //insert 一个用户
    int addUser(User user);
}
