package com.kinglead.demo.controller;

import com.kinglead.demo.entity.User;
import com.kinglead.demo.enums.GenderEnum;
import com.kinglead.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**注入UserService**/
    @Resource
    private UserService userService;

    /**
     * 插入用户
     */
    @GetMapping("/insert")
    public void insert(){
        User user = new User();
        user.setName("1312321");
        user.setAge(18L);
        user.setEmail("343434@qq.com");
        user.setGender(GenderEnum.MALE);
        //查询用户列表
        userService.insert(user);
    }

    /**
     * 查询用户列表
     */
    @GetMapping("/userList")
    public Page<User> queryAll(){
        //注意，前端页面的页面是从1开始，而JPA是从0开始
        Pageable pageable = PageRequest.of(0,5);
        //查询用户列表
        return userService.queryAll("kinglead",null,pageable);
    }

}