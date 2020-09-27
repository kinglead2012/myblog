package com.kinglead.demo.entity;

import com.kinglead.demo.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data //添加getter、setter方法
@NoArgsConstructor   //无参构造函数
@AllArgsConstructor  //所以参数构造函数
public class User implements Serializable {
    private static final long serialVersionUID = -21070736985722463L;

    private Long id;

    private String name;

    private Long age;

    private String email;

    private GenderEnum gender;

}