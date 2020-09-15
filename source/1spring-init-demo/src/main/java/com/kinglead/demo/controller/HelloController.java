package com.kinglead.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kinglead
 * @date 2020-09-08 下午 15:16
 * @describe 欢迎页控制器
 */

//@Controller标记类为控制器，能让spring自动扫描到它，添加到容器中
//另外@RestController = @Controller + @ResponseBody，@ResponseBody标记返回报文是json格式
//@RequestMapping是请求url路径映射
@Controller
@RequestMapping("/")
public class HelloController {

    //@GetMapping get请求方法路径映射
    @GetMapping("/")
    public String hello(){
        return "hello";  //返回的string值，将会被解析为视图的逻辑名
    }

}
