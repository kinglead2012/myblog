package com.kinglead.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kinglead.demo.domain.User;
import com.kinglead.demo.mapper.UserMapper;
import com.kinglead.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> queryUserList() {
        //new分页模型
        //current:当前页
        //size:每页显示条数
        Page<User> page = new Page<User>(1,5);
        //使用mybatis-plus公共查询接口完成列表查询
        Page<User> userPage = userMapper.selectPage(page, null);
        return userPage.getRecords();
    }

    @Override
    public void insert(User user){
        userMapper.insert(user);
    }
}
