package com.kinglead.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data //添加getter、setter方法
@NoArgsConstructor   //无参构造函数
@AllArgsConstructor  //所以参数构造函数
public class User implements Serializable {
    private static final long serialVersionUID = -21070736985722463L;
    /**
    * id:主键
    */
    private Long id;
    /**
    * 姓名
    */
    private String name;
    /**
     * 密码
     */
    private String password;

}