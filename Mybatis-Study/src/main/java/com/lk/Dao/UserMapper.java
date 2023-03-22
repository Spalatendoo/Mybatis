package com.lk.Dao;

import com.lk.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {


    //查询全部用户
    List<User> getUserList();

//    根据id查询用户
    User getUserById(int id);
    User getUserById2(Map<String,Object> map);


    //insert 一个用户
    int addUser(User user);

    //修改一个用户
    int updateUser(User user);

    //删除一个用户
    int deleteUser(int id);

}
