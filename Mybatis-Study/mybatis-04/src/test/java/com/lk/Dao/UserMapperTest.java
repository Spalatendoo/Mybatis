package com.lk.Dao;

import com.lk.pojo.User;
import com.lk.utils.MybatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;


public class UserMapperTest {
    static Logger logger = Logger.getLogger(UserMapperTest.class);
    @Test
    public void getUserList(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        logger.info("进入getUserList方法成功");
        User user = mapper.getUserById(1);

        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void testLog4j(){
        logger.info("info:进入了testlog4j");
        logger.debug("debug:进入了testLog4j");
        logger.error("error:进入了testlog4j");
    }

    @Test
    public void getUserByLimit(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        HashMap<String, Integer> maplist = new HashMap<>();
        maplist.put("startIndex",0);
        maplist.put("pageSize",2);
        System.out.println(maplist);
        List<User> userList = mapper.getUserByLimit(maplist);
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void getUserByBounds(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //RowBounds 实现
        RowBounds rowBounds = new RowBounds(1, 2);
        //通过java代码层面实现分层；
        List<User> userlist = sqlSession.selectList("com.lk.Dao.UserMapper.getUserByBounds",null,rowBounds);
        for (User user : userlist) {
            System.out.println(user);
        }

        sqlSession.close();

    }
}
