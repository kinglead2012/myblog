package com.kinglead.demo.controller;

import com.github.pagehelper.PageInfo;
import com.kinglead.demo.entity.User;
import com.kinglead.demo.enums.GenderEnum;
import com.kinglead.demo.service.UserService;
import com.kinglead.demo.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**注入UserService**/
    @Resource
    private UserService userService;

    @RequestMapping("/insert")
    public int insert() {
        User user = new User();
        user.setName("kl");
        user.setAge(18L);
        user.setEmail("232323@qq.com");
        user.setGender(GenderEnum.MALE);
        return userService.insert(user);
    }


    /**
     * 查询用户列表
     */
    @GetMapping("/userList")
    public PageInfo<User> queryAll(){
        UserVo userVo = new UserVo();
        userVo.setPageNum(1);
        userVo.setPageSize(7);
        //查询用户列表
        PageInfo<User> userList = userService.queryAll(userVo);
        //返回
        return userList;
    }

}