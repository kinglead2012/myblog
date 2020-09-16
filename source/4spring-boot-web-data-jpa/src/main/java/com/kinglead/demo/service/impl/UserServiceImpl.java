package com.kinglead.demo.service.impl;

import com.kinglead.demo.dao.UserRepository;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 新增用户
     */
    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    /**
     * 通过用户名和密码查询用户
     */
    @Override
    public User queryByNameAndPassword(User user) {
        return userRepository.findByNameAndPassword(user.getName(), user.getPassword());
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<User> queryAll() {
        return userRepository.findAll();
    }

}
