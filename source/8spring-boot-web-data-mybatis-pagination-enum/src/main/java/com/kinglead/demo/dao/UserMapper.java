package com.kinglead.demo.dao;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.vo.UserVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    /**
     * 新增用户
     */
    int insert(User user);

    /**
     * 查询用户列表
     */
    List<User> queryAll(UserVo userVo);
}