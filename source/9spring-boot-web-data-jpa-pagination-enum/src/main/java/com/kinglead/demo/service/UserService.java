package com.kinglead.demo.service;

import com.kinglead.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 新增用户
     */
    User insert(User user);

    /**
     * 查询用户列表
     */
    Page<User> queryAll(String name,String age,Pageable pageable);

}