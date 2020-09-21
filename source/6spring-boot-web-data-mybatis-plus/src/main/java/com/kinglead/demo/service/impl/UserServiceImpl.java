package com.kinglead.demo.service.impl;

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
        //使用mybatis-plus公共查询接口完成列表查询
        return userMapper.selectList(null);
    }
}
