package com.kinglead.demo.service.impl;

import com.kinglead.demo.dao.UserDao;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 新增用户
     */
    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    /**
     * 通过用户名和密码查询用户
     */
    @Override
    public User queryByNameAndPassword(User user) {
        return userDao.findByNameAndPassword(user.getName(), user.getPassword());
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<User> queryAll() {
        return userDao.queryAll(null);
    }

}
