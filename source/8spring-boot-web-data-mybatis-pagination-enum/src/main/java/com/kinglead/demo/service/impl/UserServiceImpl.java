package com.kinglead.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kinglead.demo.dao.UserMapper;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import com.kinglead.demo.vo.UserVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    /**
     * 查询用户列表
     */
    @Override
    public PageInfo<User> queryAll(UserVo userVo) {
        PageHelper.startPage(userVo.getPageNum(),userVo.getPageSize());
        List<User> userList = userMapper.queryAll(userVo);
        return new PageInfo<>(userList);
    }

}
