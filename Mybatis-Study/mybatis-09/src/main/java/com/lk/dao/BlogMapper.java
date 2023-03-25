package com.lk.dao;

import com.lk.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //插入数据
    int addBlog(Blog blog);

    //查询博客
    List<Blog> queryBlogIF(Map map);

    //查询
    List<Blog> queryBlogChoose(Map map);

    //更新
    int updateBlog(Map map);
}
