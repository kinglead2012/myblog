package com.kinglead.demo.service;

import com.github.pagehelper.PageInfo;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.vo.UserVo;

import java.util.List;

public interface UserService {

    /**
     * 新增用户
     */
    int insert(User user);

    /**
     * 查询用户列表
     */
    PageInfo<User> queryAll(UserVo userVo);

}