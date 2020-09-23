package com.kinglead.demo.controller;

import com.kinglead.demo.domain.User;
import com.kinglead.demo.enums.GenderEnum;
import com.kinglead.demo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/userList")
    public List<User> queryUserList(){
        return userService.queryUserList();
    }

    @RequestMapping("/insert")
    public void insert(){
        User user = new User();
        user.setId(17L);
        user.setName("kinglead");
        user.setAge(19);
        user.setEmail("131312@163.com");
        user.setGender(GenderEnum.MALE);
        userService.insert(user);
    }

}
