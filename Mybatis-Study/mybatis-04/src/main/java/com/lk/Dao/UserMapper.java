package com.lk.Dao;

import com.lk.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {



//    根据id查询用户
    User getUserById(int id);
    User getUserById2(Map<String,Object> map);


    //分页
    List<User> getUserByLimit(Map<String,Integer> map);




}
