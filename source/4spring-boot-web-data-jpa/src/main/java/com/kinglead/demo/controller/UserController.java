package com.kinglead.demo.controller;

import com.kinglead.demo.entity.User;
import com.kinglead.demo.service.UserService;
import com.kinglead.demo.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    /**注入UserService**/
    @Resource
    private UserService userService;

    /**
     *首页
     */
    @GetMapping("/index")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request){
        modelAndView.addObject("userName",request.getParameter("userName"));
        modelAndView.setViewName("index");
        return modelAndView;
    }

    /**
     *注册页面
     */
    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView){
        modelAndView.setViewName("register");
        return modelAndView;
    }

    /**
     *注册
     */
    @PostMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, @Valid UserVo userVo, BindingResult bindingResult){
        //校验参数
        if(bindingResult.hasErrors()){
            modelAndView.addObject("error",bindingResult.getFieldError().getDefaultMessage());
            modelAndView.setViewName("register");
            return modelAndView;
        }
        //注册
        User user = new User();
        user.setName(userVo.getUserName());
        user.setPassword(userVo.getPassword());
        userService.insert(user);
        //注册成功返回到登录页面
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     *登录页面
     */
    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     *登录
     */
    @PostMapping("/login")
    public ModelAndView login(ModelAndView modelAndView,@Valid UserVo userVo, BindingResult bindingResult){
        //效验入参
        if(bindingResult.hasErrors()){
            modelAndView.addObject("error",bindingResult.getFieldError().getDefaultMessage());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        //效验用户
        User user = new User();
        user.setName(userVo.getUserName());
        user.setPassword(userVo.getPassword());
        User rstUser = userService.queryByNameAndPassword(user);
        if(null == rstUser){
            modelAndView.addObject("error","用户名或密码错误！");
            modelAndView.setViewName("login");
        }
        //展示首页
        modelAndView.addObject("userName",rstUser.getName());
        modelAndView.setViewName("redirect:/user/index");
        return modelAndView;
    }

    /**
     * 查询用户列表
     */
    @GetMapping("/userList")
    public ModelAndView queryAll(ModelAndView modelAndView){
        //查询用户列表
        List<User> userList = userService.queryAll();
        //返回
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("userList");
        return modelAndView;
    }

}