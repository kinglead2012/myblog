package com.kinglead.demo.dao;

import com.kinglead.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {

    /**
     * 新增用户
     */
    int insert(User user);

    /**
     * 通过用户名和密码查询用户
     */
    User findByNameAndPassword(@Param("name")String name, @Param("password") String password);

    /**
     * 查询用户列表
     */
    List<User> queryAll(User user);
}