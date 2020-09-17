package com.kinglead.demo.service;

import com.kinglead.demo.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 新增用户
     */
    int insert(User user);

    /**
     * 通过用户名和密码查询用户
     */
    User queryByNameAndPassword(User user);

    /**
     * 查询用户列表
     */
    List<User> queryAll();

}