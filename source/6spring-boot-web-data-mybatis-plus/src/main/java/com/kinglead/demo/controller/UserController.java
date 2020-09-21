package com.kinglead.demo.controller;

import com.kinglead.demo.domain.User;
import com.kinglead.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/userList")
    public ModelAndView queryUserList(ModelAndView modelAndView){
        List<User> userList = userService.queryUserList();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("userList");
        return modelAndView;
    }

}
