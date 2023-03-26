package com.lk.dao;

import com.lk.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    //根据id查询用户
    User queryUserID(@Param("id") int id);

    int updateUser(User user);

}
